package com.kitnet.kitnet.service;

import com.kitnet.kitnet.model.Property;
import com.kitnet.kitnet.model.PropertyImage;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.exception.FileUploadException;
import com.kitnet.kitnet.exception.InvalidFileFormatException;
import com.kitnet.kitnet.exception.FileSizeExceededException;
import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.model.enums.PropertyAccountVerificationStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PropertyService {
    // --- Property Basic CRUD ---
    @Transactional
    Property createProperty(Property property, UUID announcingUserId) throws UserNotFoundException, InvalidOperationException;
    Property getPropertyById(UUID propertyId) throws ResourceNotFoundException;
    List<Property> getAllAvailableProperties();
    @Transactional
    Property updateProperty(UUID propertyId, Property updatedProperty, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException;
    @Transactional
    void deleteProperty(UUID propertyId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException;

    // --- Image Property Management ---
    @Transactional
    PropertyImage addPropertyImage(UUID propertyId, MultipartFile file, boolean isMainImage, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException, InvalidFileFormatException, FileSizeExceededException;
    @Transactional
    void deletePropertyImage(UUID imageId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, IOException, FileUploadException;
    @Transactional
    PropertyImage setMainPropertyImage(UUID propertyId, UUID imageId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException;
    List<PropertyImage> getPropertyImages(UUID propertyId) throws ResourceNotFoundException;

    // --- Status Verification Management (Admin/Moderador) ---
    @Transactional
    Property updatePropertyVerificationStatus(UUID propertyId, PropertyAccountVerificationStatus status, String rejectionReason, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException;
}