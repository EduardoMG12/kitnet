package com.kitnet.kitnet.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DocumentType {
    RG_FRENTE,
    RG_VERSO,
    CPF,
    CNH_FRENTE,
    CNH_VERSO,
    PASSAPORTE,
    CNPJ,
    CONTRATO_SOCIAL,
    COMPROVANTE_INSCRICAO_ESTADUAL,
    COMPROVANTE_RESIDENCIA,
    COMPROVANTE_RENDA,
    EXTRATO_BANCARIO_ULTIMOS_MESES,
    SELFIE_COM_DOCUMENTO,
    PROVA_PROPRIEDADE_IMOVEL,
    CRECI,
    PROCURACAO,
    OUTRO;

    @JsonCreator
    public static DocumentType fromValue(String value) {
        try {
            return DocumentType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid document type: " + value);
        }
    }
}