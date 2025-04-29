package com.kitnet.kitnet.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName; // Nome

    @Column(nullable = false)
    private String lastName; // Sobrenome

    @Column(nullable = false, unique = true)
    private String email;

    private String phone; // Telefone

    @Column(nullable = false)
    private String password; // Senha

    // Não persistiremos o campo de "confirmar senha" no banco de dados.
    // Ele é usado apenas para validação no momento do registro.

    @Column(nullable = false)
    private Boolean acceptTerms; // Aceita os Termos de Uso

    @Column(nullable = false, unique = true)
    private String cpf; // CPF

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] documentImageWithUser; // Imagem do documento com a pessoa ao lado (armazenado como array de bytes)

}