package com.kitnet.kitnet.service;

import com.kitnet.kitnet.model.LegalDocument;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserLegalDocument;
import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface LegalDocumentService {

    // --- Métodos de Gestão Global de Termos (ADMIN) ---

    /**
     * Creates a new legal document or updates an existing version.
     * Ensures that for a given LegalDocumentType, only one version is globally active.
     * When a new document is set to active, all previous active versions of the same type are deactivated.
     * This method is intended for use by administrators.
     * @param legalDocument The LegalDocument entity to save or update. Must have type, version, content, effectiveDate, and isActive flag.
     * @return The saved/updated LegalDocument entity, which is now the active one for its type.
     * @throws InvalidOperationException If trying to create a version that already exists and is active, or if validation fails.
     */
    @Transactional
    LegalDocument createOrUpdateLegalDocument(LegalDocument legalDocument) throws InvalidOperationException;

    /**
     * Retrieves the globally active version of a specific legal document type.
     * @param type The LegalDocumentType to retrieve the active version for.
     * @return The active LegalDocument entity.
     * @throws ResourceNotFoundException If no active document is found for the given type.
     */
    LegalDocument getActiveLegalDocument(LegalDocumentType type) throws ResourceNotFoundException;

    /**
     * Retrieves a specific legal document by its type and version, regardless of its active status.
     * @param type The LegalDocumentType of the document.
     * @param version The specific version string of the document.
     * @return The LegalDocument entity.
     * @throws ResourceNotFoundException If the document with the specified type and version is not found.
     */
    LegalDocument getLegalDocumentByVersion(LegalDocumentType type, String version) throws ResourceNotFoundException;

    /**
     * Retrieves all legal documents in the system, including active and inactive versions.
     * @return A list of all LegalDocument entities.
     */
    List<LegalDocument> getAllLegalDocuments();

    /**
     * Retrieves all legal documents that are currently globally active across all types.
     * This list should ideally contain one document per LegalDocumentType (e.g., Terms of Use, LGPD, Privacy Policy).
     * @return A list of all active LegalDocument entities.
     */
    List<LegalDocument> getAllActiveLegalDocuments();

    // --- Métodos de Aceitação e Verificação de Termos por Usuário ---

    /**
     * Processes a user's acceptance of specified active legal document types.
     * For each accepted type:
     * - Finds the globally active version.
     * - Creates or updates a UserLegalDocument record for the user, marking it as 'isCurrentOfUser = true'.
     * - If the user previously accepted an older version of the same type, that old acceptance is marked as 'isCurrentOfUser = false'.
     * This ensures the user's acceptance always points to the latest active version.
     * @param user The User entity who is accepting the documents.
     * @param acceptedTypes A set of LegalDocumentType enums that the user has explicitly accepted.
     * @return A list of UserLegalDocument entities representing the user's updated acceptances.
     * @throws ResourceNotFoundException If an active legal document for a given type is not found globally.
     */
    @Transactional
    List<UserLegalDocument> acceptActiveLegalDocumentsForUser(User user, Set<LegalDocumentType> acceptedTypes) throws ResourceNotFoundException;

    /**
     * Checks if a user has accepted the latest globally active version of a specific legal document type.
     * This is useful for validating individual terms.
     * @param user The User entity to check.
     * @param type The LegalDocumentType to verify acceptance for.
     * @return True if the user has accepted the latest active version of the specified type, false otherwise.
     */
    boolean hasAcceptedLatestLegalDocument(User user, LegalDocumentType type);

    /**
     * Checks if the user needs to revalidate any of their critical legal documents.
     * A user needs to revalidate if they have not accepted the latest active version of ANY critical legal document.
     * This method iterates through all LegalDocumentTypes (or a predefined set of critical ones) and checks each.
     * @param user The User entity to check.
     * @return True if the user needs to revalidate any legal document, false otherwise.
     */
    boolean needsToRevalidateLegalDocuments(User user);
}