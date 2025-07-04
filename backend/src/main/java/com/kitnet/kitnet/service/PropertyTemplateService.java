package com.kitnet.kitnet.service;

import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.model.PropertyTemplate;
import com.kitnet.kitnet.model.enums.PropertyTemplateTargetRole;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface PropertyTemplateService {
    @Transactional
    PropertyTemplate createPropertyTemplate(PropertyTemplate template, UUID actingUserId) throws InvalidOperationException, UnauthorizedOperationException;
    PropertyTemplate getPropertyTemplateById(UUID templateId) throws ResourceNotFoundException;
    PropertyTemplate getPropertyTemplateByName(String templateName) throws ResourceNotFoundException;
    List<PropertyTemplate> getAllActiveTemplates();
    List<PropertyTemplate> getActiveTemplatesByTargetRole(PropertyTemplateTargetRole targetRole);
    @Transactional
    PropertyTemplate updatePropertyTemplate(UUID templateId, PropertyTemplate updatedTemplate, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException;
    @Transactional
    void deletePropertyTemplate(UUID templateId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException;
    @Transactional
    PropertyTemplate activatePropertyTemplate(UUID templateId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException;
    @Transactional
    PropertyTemplate deactivatePropertyTemplate(UUID templateId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException;
}