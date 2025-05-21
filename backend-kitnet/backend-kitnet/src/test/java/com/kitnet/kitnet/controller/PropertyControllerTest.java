package com.kitnet.kitnet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Importar esta anotação
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
// Adicione esta anotação para desabilitar os filtros de segurança
@AutoConfigureMockMvc(addFilters = false)
@Import(PropertyControllerTest.PropertyServiceTestConfig.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertyService propertyService;

    @TestConfiguration
    static class PropertyServiceTestConfig {
        @Bean
        public PropertyService propertyService() {
            return mock(PropertyService.class);
        }
    }

    // O método getBasicAuthHeader não é mais estritamente necessário se a segurança for desabilitada,
    // mas pode ser mantido se você planeja reabilitar a segurança para testes de integração futuros.
    private String getBasicAuthHeader() {
        String username = "user";
        String password = "senha";
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Test
    void testGetAllProperties() throws Exception {
        PropertyResponseDto responseDto = new PropertyResponseDto();
        responseDto.setId(1L);
        responseDto.setAdTitle("Nice Kitnet");
        responseDto.setCity("Curitiba");
        responseDto.setState("PR");
        responseDto.setPurpose("Rent");
        responseDto.setRentValue(1200.0);
        responseDto.setPropertyType("Kitnet");
        responseDto.setOwnerConfirmation(true);

        when(propertyService.findAll()).thenReturn(Collections.singletonList(responseDto));

        mockMvc.perform(get("/properties")
                        // Remova o cabeçalho de autorização se a segurança estiver desabilitada
                        // .header("Authorization", getBasicAuthHeader())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adTitle").value("Nice Kitnet"));
    }

    @Test
    void testCreateProperty() throws Exception {
        PropertyRequestDto requestDto = new PropertyRequestDto();
        requestDto.setAdTitle("Nice Kitnet");
        requestDto.setCity("Curitiba");
        requestDto.setState("PR");
        requestDto.setPurpose("Rent");
        requestDto.setRentValue(1200.0);
        requestDto.setPropertyType("Kitnet");
        requestDto.setOwnerConfirmation(true);
        requestDto.setTermsAgreement(true);

        PropertyResponseDto responseDto = new PropertyResponseDto();
        responseDto.setId(1L);
        responseDto.setAdTitle("Nice Kitnet");
        responseDto.setCity("Curitiba");
        responseDto.setState("PR");
        responseDto.setPurpose("Rent");
        responseDto.setRentValue(1200.0);
        responseDto.setPropertyType("Kitnet");
        responseDto.setOwnerConfirmation(true);

        when(propertyService.create(any())).thenReturn(responseDto);

        mockMvc.perform(post("/properties")
                        // Remova o cabeçalho de autorização se a segurança estiver desabilitada
                        // .header("Authorization", getBasicAuthHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.adTitle").value("Nice Kitnet"));
    }
}
