package com.kitnet.kitnet.model.enums;

public enum RentalStatus {
    ACTIVE,          // Contrato ativo e em vigor
    PENDING_APPROVAL, // Uma alteração (ex: preço) foi proposta e aguarda aprovação
    TERMINATED,       // Contrato finalizado normalmente (fim da data ou por acordo mútuo)
    BROKEN,           // Contrato quebrado unilateralmente ou por violação
    CANCELED          // Contrato que nunca chegou a ser ativado ou foi cancelado antes do início
}