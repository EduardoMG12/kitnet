package com.kitnet.kitnet.model;

public enum DocumentType {
    // Documents PF
    RG_FRENTE,
    RG_VERSO,
    CPF,
    CNH_FRENTE,
    CNH_VERSO,
    PASSAPORTE,

    // Documents PJ
    CNPJ,
    CONTRATO_SOCIAL,
    COMPROVANTE_INSCRICAO_ESTADUAL,

    // Comprovantes
    COMPROVANTE_RESIDENCIA,
    COMPROVANTE_RENDA, // Holerite, extrato bancário, declaração de imposto de renda
    EXTRATO_BANCARIO_ULTIMOS_MESES, // Pode ser separado para análise financeira mais profunda

    // Outros
    SELFIE_COM_DOCUMENTO, // Selfie segurando o documento de identidade
    PROVA_PROPRIEDADE_IMOVEL, // Matrícula do imóvel, escritura
    CRECI, // Para corretores
    PROCURACAO, // Se for um procurador
    OUTRO // Para casos não listados, com descrição no campo 'documentType' na entidade UserDocument, se for String
}