package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.rental.RentalRequestDTO;
import com.kitnet.kitnet.dto.rental.RentalResponseDTO;
import com.kitnet.kitnet.dto.rental.RentalUpdateDTO;

import com.kitnet.kitnet.model.*;
import com.kitnet.kitnet.repository.PropertyRepository;
import com.kitnet.kitnet.repository.RentalRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.RentalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    private RentalResponseDTO convertToDto(Rental rental) {
        RentalResponseDTO dto = new RentalResponseDTO();
        BeanUtils.copyProperties(rental, dto);
        dto.setTenantId(rental.getTenant().getId());
        dto.setTenantEmail(rental.getTenant().getEmail());
        dto.setPropertyId(rental.getProperty().getId());
        dto.setPropertyTitle(rental.getProperty().getAdTitle());
        dto.setOwnerId(rental.getProperty().getOwner().getId());
        dto.setOwnerEmail(rental.getProperty().getOwner().getEmail());
        return dto;
    }

    @Override
    @Transactional
    public RentalResponseDTO createRental(RentalRequestDTO rentalDto, User authenticatedUser) {
        User tenant = userRepository.findById(rentalDto.getTenantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Locatário não encontrado."));
        Property property = propertyRepository.findById(rentalDto.getPropertyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada."));

        // CORRIGIDO: Usando hasRole()
        if (authenticatedUser.hasRole(RoleName.LESSEE) &&
                (authenticatedUser.getMonthlyGrossIncome() == null || authenticatedUser.getMonthlyGrossIncome() <= 0 || authenticatedUser.getHasCreditRestrictions())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seu perfil financeiro não atende aos requisitos para alugar. Por favor, complete seu cadastro ou entre em contato com o suporte.");
        }

        // CORRIGIDO: Usando hasRole()
        if ((!property.getOwner().getId().equals(authenticatedUser.getId()) &&
                !authenticatedUser.hasRole(RoleName.REAL_ESTATE_AGENT)) || // Corrigido para verificar se NÃO é REAL_ESTATE_AGENT
                (authenticatedUser.hasRole(RoleName.LESSOR) && authenticatedUser.getAccountVerificationStatus() != VerificationStatus.APPROVED) ||
                (authenticatedUser.hasRole(RoleName.REAL_ESTATE_AGENT) && authenticatedUser.getAccountVerificationStatus() != VerificationStatus.APPROVED)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente um proprietário ou corretor verificado pode criar um aluguel para esta propriedade.");
        }

        if (rentalDto.getStartDate().isAfter(rentalDto.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de início não pode ser depois da data de término.");
        }

        if (authenticatedUser.getAccountVerificationStatus() != VerificationStatus.APPROVED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sua conta precisa ser verificada para realizar esta ação.");
        }

        List<Rental> conflictingRentals = rentalRepository.findConflictingRentals(
                property.getId(),
                rentalDto.getStartDate(),
                rentalDto.getEndDate(),
                Arrays.asList(RentalStatus.ACTIVE, RentalStatus.PENDING_APPROVAL)
        );

        if (!conflictingRentals.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A propriedade já está alugada ou tem uma proposta pendente para o período solicitado.");
        }

        Rental rental = new Rental();
        BeanUtils.copyProperties(rentalDto, rental);
        rental.setTenant(tenant);
        rental.setProperty(property);
        rental.setActive(true);
        rental.setStatus(RentalStatus.ACTIVE); // Contrato ativo ao ser criado

        Rental savedRental = rentalRepository.save(rental);
        return convertToDto(savedRental);
    }

    @Override
    public List<RentalResponseDTO> findAllRentals(User authenticatedUser) {
        // Filtrar aluguéis com base no tipo de usuário autenticado
        List<Rental> rentals;
        // CORRIGIDO: Usando hasRole()
        if (authenticatedUser.hasRole(RoleName.LESSOR) || authenticatedUser.hasRole(RoleName.REAL_ESTATE_AGENT)) {
            // Locadores/Corretores podem ver todos os aluguéis das suas propriedades
            rentals = rentalRepository.findAll().stream()
                    .filter(r -> r.getProperty().getOwner().getId().equals(authenticatedUser.getId()))
                    .collect(Collectors.toList());
            // CORRIGIDO: Usando hasRole()
        } else if (authenticatedUser.hasRole(RoleName.LESSEE)) {
            // Locatários veem apenas os seus próprios aluguéis
            rentals = rentalRepository.findByTenantId(authenticatedUser.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado.");
        }
        return rentals.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public RentalResponseDTO findRentalById(UUID id, User authenticatedUser) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // Permissão: Locador/Corretor da propriedade OU Locatário do aluguel
        // CORRIGIDO: Usando hasRole()
        boolean isOwnerOrAgent = (rental.getProperty().getOwner().getId().equals(authenticatedUser.getId()) ||
                authenticatedUser.hasRole(RoleName.REAL_ESTATE_AGENT));
        boolean isTenant = rental.getTenant().getId().equals(authenticatedUser.getId());

        if (!isOwnerOrAgent && !isTenant) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para visualizar este aluguel.");
        }

        return convertToDto(rental);
    }

    @Override
    @Transactional
    public RentalResponseDTO updateRental(UUID id, RentalUpdateDTO updateDto, User authenticatedUser) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // CORRIGIDO: Usando hasRole()
        boolean isOwnerOrAgent = (rental.getProperty().getOwner().getId().equals(authenticatedUser.getId()) ||
                authenticatedUser.hasRole(RoleName.REAL_ESTATE_AGENT));
        boolean isTenant = rental.getTenant().getId().equals(authenticatedUser.getId());

        // Lógica de ações específicas
        if (updateDto.getAction() != null) {
            switch (updateDto.getAction()) {
                case "PROPOSE_PRICE_CHANGE":
                    if (!isOwnerOrAgent) { // Apenas proprietário/corretor pode propor mudança de preço
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o proprietário ou corretor pode propor alteração de preço.");
                    }
                    return proposePriceChange(id, updateDto.getProposedMonthlyRent(), authenticatedUser);
                case "APPROVE_PRICE_CHANGE":
                    if (!isTenant) { // Apenas locatário pode aprovar
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o locatário pode aprovar a alteração de preço.");
                    }
                    return approvePriceChange(id, authenticatedUser);
                case "REJECT_PRICE_CHANGE":
                    if (!isTenant) { // Apenas locatário pode rejeitar
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o locatário pode rejeitar a alteração de preço.");
                    }
                    return rejectPriceChange(id, authenticatedUser);
                case "TERMINATE_CONTRACT":
                    return terminateContract(id, authenticatedUser);
                case "BREAK_CONTRACT":
                    return breakContract(id, authenticatedUser);
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ação desconhecida: " + updateDto.getAction());
            }
        }

        // Se não houver ação específica, permitir atualização de campos gerais
        // Apenas o proprietário/corretor pode atualizar informações gerais do contrato
        if (!isOwnerOrAgent) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para atualizar este aluguel.");
        }

        // Exemplo: atualizar data de término (se não houver proposta de preço pendente)
        if (updateDto.getEffectiveDate() != null && rental.getStatus() != RentalStatus.PENDING_APPROVAL) {
            rental.setEndDate(updateDto.getEffectiveDate());
        }

        Rental updatedRental = rentalRepository.save(rental);
        return convertToDto(updatedRental);
    }

    @Override
    @Transactional
    public void deleteRental(UUID id, User authenticatedUser) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // Somente o proprietário da propriedade ou um corretor pode deletar um aluguel
        // CORRIGIDO: Usando hasRole()
        boolean isOwnerOrAgent = (rental.getProperty().getOwner().getId().equals(authenticatedUser.getId()) ||
                authenticatedUser.hasRole(RoleName.REAL_ESTATE_AGENT));

        if (!isOwnerOrAgent) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o proprietário ou corretor pode excluir este aluguel.");
        }
        rentalRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RentalResponseDTO proposePriceChange(UUID rentalId, Double newPrice, User requestingUser) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // O solicitante deve ser o proprietário da propriedade ou um corretor
        // CORRIGIDO: Usando hasRole()
        boolean isOwnerOrAgent = (rental.getProperty().getOwner().getId().equals(requestingUser.getId()) ||
                requestingUser.hasRole(RoleName.REAL_ESTATE_AGENT));
        if (!isOwnerOrAgent) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o proprietário ou corretor pode propor alteração de preço.");
        }

        if (rental.getStatus() == RentalStatus.PENDING_APPROVAL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma proposta de alteração de preço pendente para este aluguel.");
        }

        rental.setProposedMonthlyRent(newPrice);
        rental.setStatus(RentalStatus.PENDING_APPROVAL);
        rental.setLastPriceUpdateDate(LocalDate.now());

        Rental updatedRental = rentalRepository.save(rental);
        return convertToDto(updatedRental);
    }

    @Override
    @Transactional
    public RentalResponseDTO approvePriceChange(UUID rentalId, User approvingUser) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // O aprovador deve ser o locatário
        if (!rental.getTenant().getId().equals(approvingUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o locatário pode aprovar a alteração de preço.");
        }

        if (rental.getStatus() != RentalStatus.PENDING_APPROVAL || rental.getProposedMonthlyRent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há proposta de alteração de preço pendente para aprovação.");
        }

        rental.setMonthlyRent(rental.getProposedMonthlyRent()); // Aplica o novo preço
        rental.setProposedMonthlyRent(null);
        rental.setStatus(RentalStatus.ACTIVE);

        Rental updatedRental = rentalRepository.save(rental);
        return convertToDto(updatedRental);
    }

    @Override
    @Transactional
    public RentalResponseDTO rejectPriceChange(UUID rentalId, User rejectingUser) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // O rejeitador deve ser o locatário
        if (!rental.getTenant().getId().equals(rejectingUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o locatário pode rejeitar a alteração de preço.");
        }

        if (rental.getStatus() != RentalStatus.PENDING_APPROVAL || rental.getProposedMonthlyRent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há proposta de alteração de preço pendente para rejeitar.");
        }

        rental.setProposedMonthlyRent(null); // Limpa o valor proposto
        rental.setStatus(RentalStatus.ACTIVE);

        Rental updatedRental = rentalRepository.save(rental);
        return convertToDto(updatedRental);
    }

    @Override
    @Transactional
    public RentalResponseDTO terminateContract(UUID rentalId, User requestingUser) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // CORRIGIDO: Usando hasRole()
        boolean isOwnerOrAgent = (rental.getProperty().getOwner().getId().equals(requestingUser.getId()) ||
                requestingUser.hasRole(RoleName.REAL_ESTATE_AGENT));
        boolean isTenant = rental.getTenant().getId().equals(requestingUser.getId());

        if (!isOwnerOrAgent && !isTenant) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o locador/corretor ou locatário pode solicitar o término do contrato.");
        }

        if (rental.getStatus() != RentalStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O contrato não está ativo para ser terminado.");
        }

        rental.setEndDate(LocalDate.now());
        rental.setActive(false);
        rental.setStatus(RentalStatus.TERMINATED);

        Rental updatedRental = rentalRepository.save(rental);
        return convertToDto(updatedRental);
    }

    @Override
    @Transactional
    public RentalResponseDTO breakContract(UUID rentalId, User requestingUser) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguel não encontrado."));

        // CORRIGIDO: Usando hasRole()
        boolean isOwnerOrAgent = (rental.getProperty().getOwner().getId().equals(requestingUser.getId()) ||
                requestingUser.hasRole(RoleName.REAL_ESTATE_AGENT));
        boolean isTenant = rental.getTenant().getId().equals(requestingUser.getId());

        if (!isOwnerOrAgent && !isTenant) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Somente o locador/corretor ou locatário pode quebrar o contrato.");
        }

        if (rental.getStatus() != RentalStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O contrato não está ativo para ser quebrado.");
        }

        // Aqui, estamos apenas mudando o status e desativando.
        // futuramente implementar que "quebra de contrato" implicara em multa, etc.
        rental.setEndDate(LocalDate.now());
        rental.setActive(false);
        rental.setStatus(RentalStatus.BROKEN);

        Rental updatedRental = rentalRepository.save(rental);
        return convertToDto(updatedRental);
    }
}