package com.kitnet.kitnet.model.enums;

public enum AccountStatus {
    ACTIVE,         // Conta normal, em pleno funcionamento
    SUSPENDED,      // Suspensa temporariamente (ex: por violação leve das regras)
    BANNED,         // Banida permanentemente (ex: por violação grave, fraude)
    INACTIVE        // Conta que pode ter sido criada mas nunca ativada ou inativada pelo usuário
}