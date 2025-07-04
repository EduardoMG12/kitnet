package com.kitnet.kitnet.service;

import com.kitnet.kitnet.model.PropertyListing;

import com.kitnet.kitnet.model.enums.ListingModerationStatus;
import com.kitnet.kitnet.model.enums.ListingVisibility;
import com.kitnet.kitnet.exception.ResourceNotFoundException;
import com.kitnet.kitnet.exception.InvalidOperationException;
import com.kitnet.kitnet.exception.UnauthorizedOperationException;
import com.kitnet.kitnet.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface PropertyListingService {
    // --- Basic CRUD PropertyListing ---
    /**
     * Cria uma nova listagem (anúncio) para um imóvel existente.
     * @param listing O objeto PropertyListing com os dados da listagem.
     * @param propertyId O ID do imóvel ao qual esta listagem pertence.
     * @param listerId O ID do usuário que está criando esta listagem.
     * @param templateId O ID opcional do template a ser usado.
     * @return A listagem criada.
     * @throws ResourceNotFoundException Se o imóvel ou o lister não forem encontrados.
     * @throws UnauthorizedOperationException Se o lister não tem permissão para criar listagem para este imóvel.
     * @throws InvalidOperationException Se a lógica de negócio impedir a criação (ex: template premium).
     */
    @Transactional
    PropertyListing createListing(PropertyListing listing, UUID propertyId, UUID listerId, UUID templateId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException;

    /**
     * Obtém uma listagem pelo seu ID, com todos os detalhes.
     * @param listingId O ID da listagem.
     * @return A PropertyListing.
     * @throws ResourceNotFoundException Se a listagem não for encontrada.
     */
    PropertyListing getListingById(UUID listingId) throws ResourceNotFoundException;

    /**
     * Lista todas as listagens que estão públicas e ativas.
     * @return Uma lista de PropertyListing.
     */
    List<PropertyListing> getAllPublicAndActiveListings();

    /**
     * Atualiza os detalhes de uma listagem.
     * @param listingId O ID da listagem a ser atualizada.
     * @param updatedListing Os novos dados da listagem.
     * @param actingUserId O ID do usuário que está realizando a atualização.
     * @return A listagem atualizada.
     * @throws ResourceNotFoundException Se a listagem não for encontrada.
     * @throws UnauthorizedOperationException Se o usuário não tem permissão para atualizar esta listagem.
     * @throws InvalidOperationException Se a lógica de negócio impedir a atualização.
     */
    @Transactional
    PropertyListing updateListing(UUID listingId, PropertyListing updatedListing, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException;

    /**
     * Exclui uma listagem.
     * @param listingId O ID da listagem a ser excluída.
     * @param actingUserId O ID do usuário que está realizando a exclusão.
     * @throws ResourceNotFoundException Se a listagem não for encontrada.
     * @throws UnauthorizedOperationException Se o usuário não tem permissão para excluir esta listagem.
     */
    @Transactional
    void deleteListing(UUID listingId, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException;

    // --- Manager Status Moderation (Admin/Moderador) ---
    /**
     * Atualiza o status de moderação de uma listagem.
     * @param listingId O ID da listagem.
     * @param status O novo status de moderação.
     * @param rejectionReason Motivo da rejeição (se aplicável).
     * @param actingUserId O ID do administrador/moderador.
     * @return A listagem atualizada.
     * @throws ResourceNotFoundException Se a listagem não for encontrada.
     * @throws UnauthorizedOperationException Se o usuário não tem permissão.
     * @throws InvalidOperationException Se a operação for inválida (ex: rejeitar sem motivo).
     */
    @Transactional
    PropertyListing updateListingModerationStatus(UUID listingId, ListingModerationStatus status, String rejectionReason, UUID actingUserId) throws ResourceNotFoundException, UnauthorizedOperationException, InvalidOperationException;

    // --- Métodos de Busca para o Frontend (por usuário, visibilidade) ---
    List<PropertyListing> getListingsByLister(UUID listerId) throws UserNotFoundException;
    List<PropertyListing> getListingsByProperty(UUID propertyId) throws ResourceNotFoundException;
    List<PropertyListing> getActiveListingsByVisibility(ListingVisibility visibility);
}