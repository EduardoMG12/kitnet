package com.kitnet.kitnet.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PropertyResponseDtoTest {

    @Test
    void testNoArgsConstructor() {
        // Testa o construtor sem argumentos e se os campos são inicializados para null/default
        PropertyResponseDto dto = new PropertyResponseDto();
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getAdTitle());
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        // Testa o construtor com todos os argumentos e os getters
        Long id = 1L;
        String propertyType = "House";
        String adTitle = "Beautiful House for Sale";
        String description = "Spacious house with a garden.";
        String purpose = "Sale";
        Double rentValue = 500000.0;
        String city = "Rio de Janeiro";
        String state = "RJ";
        Boolean ownerConfirmation = true;

        PropertyResponseDto dto = new PropertyResponseDto(
                id, propertyType, adTitle, description, purpose, rentValue, city, state, ownerConfirmation
        );

        assertEquals(id, dto.getId());
        assertEquals(propertyType, dto.getPropertyType());
        assertEquals(adTitle, dto.getAdTitle());
        assertEquals(description, dto.getDescription());
        assertEquals(purpose, dto.getPurpose());
        assertEquals(rentValue, dto.getRentValue());
        assertEquals(city, dto.getCity());
        assertEquals(state, dto.getState());
        assertEquals(ownerConfirmation, dto.getOwnerConfirmation());
    }

    @Test
    void testSetters() {
        // Testa os setters
        PropertyResponseDto dto = new PropertyResponseDto();

        dto.setId(2L);
        assertEquals(2L, dto.getId());

        dto.setAdTitle("Updated Ad Title");
        assertEquals("Updated Ad Title", dto.getAdTitle());

        dto.setRentValue(1800.0);
        assertEquals(1800.0, dto.getRentValue());
    }

    @Test
    void testEqualsAndHashCode() {
        // Testa os métodos equals e hashCode gerados pelo Lombok
        PropertyResponseDto dto1 = new PropertyResponseDto();
        dto1.setId(1L);
        dto1.setAdTitle("Test Ad");

        PropertyResponseDto dto2 = new PropertyResponseDto();
        dto2.setId(1L);
        dto2.setAdTitle("Test Ad");

        PropertyResponseDto dto3 = new PropertyResponseDto();
        dto3.setId(2L);
        dto3.setAdTitle("Another Ad");

        // Objetos com os mesmos valores devem ser iguais e ter o mesmo hashCode
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Objetos com valores diferentes não devem ser iguais
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
