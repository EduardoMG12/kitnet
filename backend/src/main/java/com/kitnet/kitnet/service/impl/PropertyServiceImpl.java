package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.exception.FileSizeExceededException;
import com.kitnet.kitnet.exception.FileUploadException;
import com.kitnet.kitnet.exception.InvalidFileFormatException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.PropertyImage;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.*;
import com.kitnet.kitnet.repository.PropertyImageRepository;
import com.kitnet.kitnet.repository.PropertyRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.PropertyService;
import com.kitnet.kitnet.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyImageRepository propertyImageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public Property createProperty(Property property, UUID announcingUserId) throws UserNotFoundException, InvalidOperationException {
        Locale locale = LocaleContextHolder.getLocale();

        User announcingUser = userRepository.findById(announcingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.announcing.user.not.found", null, locale)));

        if(announcingUser.hasRole(RoleName.LESSOR)) {
            property.setAnnouncingUserRole(AnnouncingUserRole.LESSOR);
            property.setOwner(announcingUser);
            property.setAgent(null);
            property.setOwnerConfirmation(true);
        } else if (announcingUser.hasRole(RoleName.REAL_ESTATE_AGENT)) {
            property.setAnnouncingUserRole(AnnouncingUserRole.REAL_ESTATE_AGENT);
            property.setOwner(announcingUser);
            property.setAgent(announcingUser);
            property.setOwnerConfirmation(false);
        } else {
            throw new InvalidOperationException(messageSource.getMessage("error.announcing.role.invalid", null, locale));
        }
        property.setOwner(announcingUser);
//        property.setAgent(announcingUserRoleName.equals(RoleName.REAL_ESTATE_AGENT.name()) ? announcingUser : null); // Define o agente se for corretor

        // Se o anunciante é um agente, o owner_confirmation deve ser false inicialmente, aguardando confirmação do proprietário
        if (property.getAnnouncingUserRole() == AnnouncingUserRole.REAL_ESTATE_AGENT) {
            property.setOwnerConfirmation(false);
        } else {
            property.setOwnerConfirmation(true);
        }

        // Status inicial de verificação
        property.setAccountVerificationStatus(PropertyAccountVerificationStatus.NOT_SUBMITTED);
        property.setIsAvailable(true); // Por padrão, é criado como disponível

        return propertyRepository.save(property);
    }

    @Override
    @Transactional(readOnly = true)
    public Property getPropertyById(UUID propertyId) throws ResourceNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        return propertyRepository.findByIdWithUsers(propertyId) // Usar findByIdWithUsers para carregar owner e agent
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.property.not.found", new Object[]{propertyId}, locale)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getAllAvailableProperties() {
        return propertyRepository.findAllAvailableWithUsers(); // Usar para carregar users
    }

    @Override
    @Transactional
    public Property updateProperty(UUID propertyId, Property updatedProperty, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        Property existingProperty = getPropertyById(propertyId); // Usar o getter que carrega users

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Regra de Autorização: Apenas o owner, o agent ou um ADMIN pode atualizar
        boolean isOwner = existingProperty.getOwner().getId().equals(actingUserId);
        boolean isAgent = existingProperty.getAgent() != null && existingProperty.getAgent().getId().equals(actingUserId);
        boolean isAdmin = actingUser.hasRole(RoleName.ADMIN);

        if (!isOwner && !isAgent && !isAdmin) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.property.update.unauthorized", null, locale));
        }

        // Atualizar campos permitidos (evitar que usuário normal altere owner/agent diretamente)
        existingProperty.setTitle(updatedProperty.getTitle());
        existingProperty.setDescription(updatedProperty.getDescription());
        existingProperty.setType(updatedProperty.getType());
        existingProperty.setPurpose(updatedProperty.getPurpose());
        existingProperty.setRentalPrice(updatedProperty.getRentalPrice());
        existingProperty.setSalePrice(updatedProperty.getSalePrice());
        existingProperty.setShowRentalPrice(updatedProperty.getShowRentalPrice());
        existingProperty.setShowSalePrice(updatedProperty.getShowSalePrice());
        existingProperty.setZipCode(updatedProperty.getZipCode());
        existingProperty.setState(updatedProperty.getState());
        existingProperty.setCity(updatedProperty.getCity());
        existingProperty.setNeighborhood(updatedProperty.getNeighborhood());
        existingProperty.setAddressStreet(updatedProperty.getAddressStreet());
        existingProperty.setAddressComplement(updatedProperty.getAddressComplement());
        existingProperty.setHideExactAddress(updatedProperty.getHideExactAddress());
        existingProperty.setLatitude(updatedProperty.getLatitude());
        existingProperty.setLongitude(updatedProperty.getLongitude());
        existingProperty.setSquareMeters(updatedProperty.getSquareMeters());
        existingProperty.setBuiltArea(updatedProperty.getBuiltArea());
        existingProperty.setBedrooms(updatedProperty.getBedrooms());
        existingProperty.setBathrooms(updatedProperty.getBathrooms());
        existingProperty.setParkingSpaces(updatedProperty.getParkingSpaces());
        existingProperty.setAcceptsPets(updatedProperty.getAcceptsPets());
        existingProperty.setAmenities(updatedProperty.getAmenities());
        existingProperty.setFloor(updatedProperty.getFloor());
        existingProperty.setCondominiumFee(updatedProperty.getCondominiumFee());
        existingProperty.setIsAvailable(updatedProperty.getIsAvailable());
        existingProperty.setTermsAgreement(updatedProperty.getTermsAgreement());

        // Se o agente está atualizando, pode ser que o owner_confirmation mude.
        // Se o status de verificação foi alterado manualmente por admin, não sobrescrever.
        // Apenas ADMINs podem mudar accountVerificationStatus e rejectionReason.
        if (!isAdmin) {
            // Não permitir que não-admin mude o status de moderação
            existingProperty.setAccountVerificationStatus(existingProperty.getAccountVerificationStatus());
            existingProperty.setRejectionReason(existingProperty.getRejectionReason());
        }

        return propertyRepository.save(existingProperty);
    }

    @Override
    @Transactional
    public void deleteProperty(UUID propertyId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException {
        Locale locale = LocaleContextHolder.getLocale();
        Property propertyToDelete = getPropertyById(propertyId); // Carrega a propriedade com seus usuários

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Regra de Autorização: Apenas o owner ou um ADMIN pode deletar
        boolean isOwner = propertyToDelete.getOwner().getId().equals(actingUserId);
        boolean isAdmin = actingUser.hasRole(RoleName.ADMIN);

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.property.delete.unauthorized", null, locale));
        }

        // Deletar todas as imagens físicas associadas à propriedade
        for (PropertyImage image : propertyToDelete.getImages()) { // Assuming FetchType.EAGER or loaded
            try {
                uploadService.deleteFile(image.getImageUrl());
            } catch (IOException | FileUploadException e) {
                System.err.println("Failed to delete physical image file for property " + propertyId + ": " + e.getMessage());
            }
        }

        propertyRepository.delete(propertyToDelete);
    }

    @Override
    @Transactional
    public PropertyImage addPropertyImage(UUID propertyId, MultipartFile file, boolean isMainImage, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException, InvalidFileFormatException, FileSizeExceededException {
        Locale locale = LocaleContextHolder.getLocale();
        Property property = getPropertyById(propertyId); // Carrega a propriedade com seus usuários

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Regra de Autorização: Apenas o owner ou um ADMIN pode adicionar imagens
        boolean isOwner = property.getOwner().getId().equals(actingUserId);
        boolean isAdmin = actingUser.hasRole(RoleName.ADMIN);
        boolean isAgent = property.getAgent() != null && property.getAgent().getId().equals(actingUserId); // Agentes também podem adicionar imagens

        if (!isOwner && !isAdmin && !isAgent) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.property.image.upload.unauthorized", null, locale));
        }

        if (file.isEmpty()) {
            throw new FileUploadException(messageSource.getMessage("error.file.empty", null, locale));
        }

        // Validação de tipo e tamanho da imagem (similar ao perfil de usuário)
        validatePropertyImageFile(file, locale);

        // Deleta a imagem principal antiga se a nova for marcada como principal
        if (isMainImage) {
            propertyImageRepository.findByPropertyAndIsMainImageTrue(property).ifPresent(oldMainImage -> {
                oldMainImage.setIsMainImage(false);
                propertyImageRepository.save(oldMainImage);
            });
        }

        String subdirectory = "properties/" + propertyId.toString() + "/images";
        String imageUrl = uploadService.uploadFile(file, subdirectory);

        PropertyImage newImage = new PropertyImage();
        newImage.setProperty(property);
        newImage.setImageUrl(imageUrl);
        newImage.setIsMainImage(isMainImage);
        // Define orderIndex: last index + 1
        newImage.setOrderIndex(property.getImages().size()); // Assuming images is FetchType.EAGER or already loaded

        return propertyImageRepository.save(newImage);
    }

    @Override
    @Transactional
    public void deletePropertyImage(UUID imageId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException {
        Locale locale = LocaleContextHolder.getLocale();
        PropertyImage imageToDelete = propertyImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.property.image.not.found", new Object[]{imageId}, locale)));

        Property property = imageToDelete.getProperty(); // Carrega a propriedade associada (pode ser lazy)
        // Inicializa a coleção de imagens para evitar LazyInitializationException se for EAGER no UserDocumentService
        // Hibernate.initialize(property.getImages()); 

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Regra de Autorização: Apenas o owner, o agent ou um ADMIN pode deletar a imagem
        boolean isOwner = property.getOwner().getId().equals(actingUserId);
        boolean isAgent = property.getAgent() != null && property.getAgent().getId().equals(actingUserId);
        boolean isAdmin = actingUser.hasRole(RoleName.ADMIN);

        if (!isOwner && !isAgent && !isAdmin) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.property.image.delete.unauthorized", null, locale));
        }

        uploadService.deleteFile(imageToDelete.getImageUrl());
        propertyImageRepository.delete(imageToDelete);
    }

    @Override
    @Transactional
    public PropertyImage setMainPropertyImage(UUID propertyId, UUID imageId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        Property property = getPropertyById(propertyId); // Carrega a propriedade com seus usuários

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Autorização
        boolean isOwner = property.getOwner().getId().equals(actingUserId);
        boolean isAgent = property.getAgent() != null && property.getAgent().getId().equals(actingUserId);
        boolean isAdmin = actingUser.hasRole(RoleName.ADMIN);

        if (!isOwner && !isAgent && !isAdmin) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.property.main.image.set.unauthorized", null, locale));
        }

        PropertyImage newMainImage = propertyImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.property.image.not.found", new Object[]{imageId}, locale)));

        if (!newMainImage.getProperty().getId().equals(propertyId)) {
            throw new InvalidOperationException(messageSource.getMessage("error.property.image.mismatch", null, locale));
        }

        // Desativar imagem principal antiga
        propertyImageRepository.findByPropertyAndIsMainImageTrue(property).ifPresent(oldMainImage -> {
            oldMainImage.setIsMainImage(false);
            propertyImageRepository.save(oldMainImage);
        });

        newMainImage.setIsMainImage(true);
        return propertyImageRepository.save(newMainImage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyImage> getPropertyImages(UUID propertyId) throws ResourceNotFoundException {
        Property property = getPropertyById(propertyId);
        return propertyImageRepository.findByPropertyOrderByOrderIndexAsc(property);
    }

    @Override
    @Transactional
    public Property updatePropertyVerificationStatus(UUID propertyId, PropertyAccountVerificationStatus status, String rejectionReason, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        Property property = getPropertyById(propertyId); // Carrega com owner/agent

        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Apenas ADMINs e MODERADORES podem mudar o status de verificação da propriedade
        if (!actingUser.hasRole(RoleName.ADMIN) && !actingUser.hasRole(RoleName.MODERATOR)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.property.verification.unauthorized", null, locale));
        }

        if (status == PropertyAccountVerificationStatus.REJECTED && (rejectionReason == null || rejectionReason.trim().isEmpty())) {
            throw new InvalidOperationException(messageSource.getMessage("error.property.rejection.reason.required", null, locale));
        }

        property.setAccountVerificationStatus(status);
        property.setRejectionReason(status == PropertyAccountVerificationStatus.REJECTED ? rejectionReason : null);

        // Se a propriedade é aprovada, setar isAvailable para true (se ainda não estiver)
        if (status == PropertyAccountVerificationStatus.APPROVED) {
            property.setIsAvailable(true);
        }

        return propertyRepository.save(property);
    }

    private void validatePropertyImageFile(MultipartFile file, Locale locale) throws FileUploadException, InvalidFileFormatException, FileSizeExceededException {
        String contentType = Objects.requireNonNull(file.getContentType());
        long fileSize = file.getSize();

        final long MAX_IMAGE_SIZE_MB = 5; // Max 5MB for property images
        if (fileSize > MAX_IMAGE_SIZE_MB * 1024 * 1024) {
            throw new FileSizeExceededException(messageSource.getMessage("error.file.size.exceeded", new Object[]{MAX_IMAGE_SIZE_MB}, locale));
        }

        if (!contentType.startsWith("image/")) {
            throw new InvalidFileFormatException(messageSource.getMessage("error.file.format.invalid", new Object[]{"image/*"}, locale));
        }
        if (!(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif") || contentType.equals("image/webp"))) {
            throw new InvalidFileFormatException(messageSource.getMessage("error.file.format.invalid.image", null, locale));
        }
    }
}