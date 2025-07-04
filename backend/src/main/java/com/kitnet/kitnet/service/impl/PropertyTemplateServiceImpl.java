
package com.kitnet.kitnet.service.impl;



import com.kitnet.kitnet.exception.InvalidOperationException;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.exception.UserNotFoundException;
import com.kitnet.kitnet.model.PropertyTemplate;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.PropertyTemplateTargetRole;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.repository.PropertyTemplateRepository;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.PropertyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyTemplateServiceImpl implements PropertyTemplateService {

    @Autowired
    private PropertyTemplateRepository propertyTemplateRepository;
    @Autowired
    private UserRepository userRepository; // Para checar roles de actingUser
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public PropertyTemplate createPropertyTemplate(PropertyTemplate template, UUID actingUserId) throws InvalidOperationException, UnauthorizedOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Apenas ADMINs podem criar templates
        if (!actingUser.hasRole(RoleName.ADMIN)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.template.creation.unauthorized", null, locale));
        }

        // Validação: template_name deve ser único
        if (propertyTemplateRepository.findByTemplateName(template.getTemplateName()).isPresent()) {
            throw new InvalidOperationException(messageSource.getMessage("error.template.name.already.exists", new Object[]{template.getTemplateName()}, locale));
        }

        // Se o novo template é ativo, desativa os outros templates ativos do mesmo targetRole
        if (template.getIsActive()) {
            propertyTemplateRepository.findByTargetRoleAndIsActiveTrue(template.getTargetRole())
                    .forEach(activeTemplate -> {
                        if (!activeTemplate.getId().equals(template.getId())) { // Evita desativar a si mesmo se for update
                            activeTemplate.setIsActive(false);
                            propertyTemplateRepository.save(activeTemplate);
                        }
                    });
        }

        return propertyTemplateRepository.save(template);
    }

    @Override
    @Transactional(readOnly = true)
    public PropertyTemplate getPropertyTemplateById(UUID templateId) throws ResourceNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        return propertyTemplateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.template.not.found", new Object[]{templateId}, locale)));
    }

    @Override
    @Transactional(readOnly = true)
    public PropertyTemplate getPropertyTemplateByName(String templateName) throws ResourceNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();
        return propertyTemplateRepository.findByTemplateName(templateName)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.template.name.not.found", new Object[]{templateName}, locale)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyTemplate> getAllActiveTemplates() {
        return propertyTemplateRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyTemplate> getActiveTemplatesByTargetRole(PropertyTemplateTargetRole targetRole) {
        return propertyTemplateRepository.findByTargetRoleAndIsActiveTrue(targetRole);
    }

    @Override
    @Transactional
    public PropertyTemplate updatePropertyTemplate(UUID templateId, PropertyTemplate updatedTemplate, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Apenas ADMINs podem atualizar templates
        if (!actingUser.hasRole(RoleName.ADMIN)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.template.update.unauthorized", null, locale));
        }

        PropertyTemplate existingTemplate = getPropertyTemplateById(templateId);

        // Validação: template_name deve permanecer único se alterado
        if (!existingTemplate.getTemplateName().equals(updatedTemplate.getTemplateName())) {
            if (propertyTemplateRepository.findByTemplateName(updatedTemplate.getTemplateName()).isPresent()) {
                throw new InvalidOperationException(messageSource.getMessage("error.template.name.already.exists", new Object[]{updatedTemplate.getTemplateName()}, locale));
            }
        }

        existingTemplate.setTemplateName(updatedTemplate.getTemplateName());
        existingTemplate.setDescription(updatedTemplate.getDescription());
        existingTemplate.setTargetRole(updatedTemplate.getTargetRole());
        existingTemplate.setIsPremium(updatedTemplate.getIsPremium());
        existingTemplate.setIsActive(updatedTemplate.getIsActive());

        // Se o template está sendo ativado, desativa os outros do mesmo tipo
        if (existingTemplate.getIsActive()) {
            propertyTemplateRepository.findByTargetRoleAndIsActiveTrue(existingTemplate.getTargetRole())
                    .forEach(activeTemplate -> {
                        if (!activeTemplate.getId().equals(existingTemplate.getId())) {
                            activeTemplate.setIsActive(false);
                            propertyTemplateRepository.save(activeTemplate);
                        }
                    });
        }

        return propertyTemplateRepository.save(existingTemplate);
    }

    @Override
    @Transactional
    public void deletePropertyTemplate(UUID templateId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        // Apenas ADMINs podem deletar templates
        if (!actingUser.hasRole(RoleName.ADMIN)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.template.delete.unauthorized", null, locale));
        }

        PropertyTemplate template = getPropertyTemplateById(templateId);
        propertyTemplateRepository.delete(template);
    }

    @Override
    @Transactional
    public PropertyTemplate activatePropertyTemplate(UUID templateId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        if (!actingUser.hasRole(RoleName.ADMIN)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.template.activation.unauthorized", null, locale));
        }

        PropertyTemplate template = getPropertyTemplateById(templateId);
        if (template.getIsActive()) {
            throw new InvalidOperationException(messageSource.getMessage("error.template.already.active", null, locale));
        }
        template.setIsActive(true);
        // Desativar outros templates ativos do mesmo tipo
        propertyTemplateRepository.findByTargetRoleAndIsActiveTrue(template.getTargetRole())
                .forEach(activeTemplate -> {
                    if (!activeTemplate.getId().equals(template.getId())) {
                        activeTemplate.setIsActive(false);
                        propertyTemplateRepository.save(activeTemplate);
                    }
                });
        return propertyTemplateRepository.save(template);
    }

    @Override
    @Transactional
    public PropertyTemplate deactivatePropertyTemplate(UUID templateId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException {
        Locale locale = LocaleContextHolder.getLocale();
        User actingUser = userRepository.findById(actingUserId)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.acting.user.not.found", null, locale)));

        if (!actingUser.hasRole(RoleName.ADMIN)) {
            throw new UnauthorizedOperationException(messageSource.getMessage("error.template.deactivation.unauthorized", null, locale));
        }

        PropertyTemplate template = getPropertyTemplateById(templateId);
        if (!template.getIsActive()) {
            throw new InvalidOperationException(messageSource.getMessage("error.template.already.inactive", null, locale));
        }
        template.setIsActive(false);
        return propertyTemplateRepository.save(template);
    }
}