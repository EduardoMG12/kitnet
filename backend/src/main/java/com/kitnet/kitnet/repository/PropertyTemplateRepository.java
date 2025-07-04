package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.PropertyTemplate;
import com.kitnet.kitnet.model.enums.PropertyTemplateTargetRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyTemplateRepository extends JpaRepository<PropertyTemplate, UUID> {
    Optional<PropertyTemplate> findByTemplateName(String templateName);
    List<PropertyTemplate> findByIsActiveTrue();
    List<PropertyTemplate> findByTargetRoleAndIsActiveTrue(PropertyTemplateTargetRole targetRole);
}