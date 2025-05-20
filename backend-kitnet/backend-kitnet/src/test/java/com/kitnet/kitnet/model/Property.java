//package com.kitnet.kitnet.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "properties")
//@Data
//public class Property {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String propertyType; // Type of property (Apartment, Commercial, House, Studio, Land)
//
//    @Column(nullable = false)
//    private String adTitle; // announcement title
//
//    @Column(columnDefinition = "TEXT")
//    private String description;
//
//    @Column(nullable = false)
//    private String purpose; // purpose (Rent, Sale, Both)
//
//    private Double rentValue;
//
//    private String zipCode; // CEP
//
//    private String state; // Estado
//
//    private String city; // Cidade
//
//    private String neighborhood; // Bairro
//
//    private String address; // Endereço
//
//    private String number; // Número
//
//    private String complement; // Complemento
//
//    private Boolean hideExactAddress; // Não mostrar endereço exato no anúncio
//
//    private Double squareMeters; // Metros quadrados
//
//    private Double builtArea; // Área construída (metros quadrados)
//
//    private Integer bedrooms; // Quartos
//
//    private Integer bathrooms; // Banheiros
//
//    private Integer parkingSpaces; // Vagas de garagem
//
//    @Column(columnDefinition = "TEXT")
//    private String amenities; // Comodidades (pode ser armazenado como uma string separada por vírgulas ou em uma tabela separada - decidiremos depois)
//
//    private Integer floor; // Andar (se aplicável)
//
//    private Double condominiumFee; // Valor do condomínio
//
//    @Column(columnDefinition = "TEXT")
//    private String photos; // Caminhos das fotos (pode ser armazenado como uma string separada por vírgulas ou em uma tabela separada - decidiremos depois)
//
//    @Column(nullable = false)
//    private Boolean ownerConfirmation; // Confirmo que sou proprietário ou autorizado
//
//    @Column(nullable = false)
//    private Boolean termsAgreement; // Concordo com os Termos de Uso
//}