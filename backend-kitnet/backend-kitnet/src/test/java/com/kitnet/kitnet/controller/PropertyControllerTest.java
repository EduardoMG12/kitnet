package com.kitnet.kitnet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitnet.kitnet.dto.PropertyRequestDTO;
import com.kitnet.kitnet.dto.PropertyResponseDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
@AutoConfigureMockMvc(addFilters = false)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertyService propertyService;

    private User currentUser;
    private UUID userId;

    @TestConfiguration
    static class PropertyServiceTestConfig {
        @Bean
        public PropertyService propertyService() {
            return mock(PropertyService.class);
        }
    }

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        currentUser = new User();
        currentUser.setId(userId);
        currentUser.setEmail("test@test.com");
    }

    @Test
    void testGetAllProperties() throws Exception {
        PropertyResponseDTO responseDto = new PropertyResponseDTO();
        responseDto.setId(UUID.randomUUID());
        responseDto.setAdTitle("Nice Kitnet");
        responseDto.setCity("Curitiba");
        responseDto.setState("PR");
        responseDto.setPurpose("Rent");
        responseDto.setRentValue(1200.0);
        responseDto.setPropertyType("Kitnet");
        responseDto.setOwnerConfirmation(true);
        responseDto.setTermsAgreement(true);

        when(propertyService.findAll()).thenReturn(Collections.singletonList(responseDto));

        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adTitle").value("Nice Kitnet"));
    }

    @Test
    void testGetMyProperties() throws Exception {
        PropertyResponseDTO responseDto = new PropertyResponseDTO();
        responseDto.setId(UUID.randomUUID());
        responseDto.setAdTitle("My Kitnet");
        responseDto.setCity("Curitiba");
        responseDto.setState("PR");
        responseDto.setPurpose("Rent");
        responseDto.setRentValue(1200.0);
        responseDto.setPropertyType("Kitnet");
        responseDto.setOwnerConfirmation(true);
        responseDto.setTermsAgreement(true);
        responseDto.setOwnerEmail("test@test.com");

        when(propertyService.findByOwner(currentUser)).thenReturn(Collections.singletonList(responseDto));

        mockMvc.perform(get("/properties/my-properties")
                        .with(user(currentUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adTitle").value("My Kitnet"))
                .andExpect(jsonPath("$[0].ownerEmail").value("test@test.com"));
    }

    @Test
    void testCreateProperty() throws Exception {
        PropertyRequestDTO requestDto = new PropertyRequestDTO();
        requestDto.setAdTitle("Nice Kitnet");
        requestDto.setCity("Curitiba");
        requestDto.setState("PR");
        requestDto.setPurpose("Rent");
        requestDto.setRentValue(1200.0);
        requestDto.setPropertyType("Kitnet");
        requestDto.setOwnerConfirmation(true);
        requestDto.setTermsAgreement(true);

        PropertyResponseDTO responseDto = new PropertyResponseDTO();
        responseDto.setId(UUID.randomUUID());
        responseDto.setAdTitle("Nice Kitnet");
        responseDto.setCity("Curitiba");
        responseDto.setState("PR");
        responseDto.setPurpose("Rent");
        responseDto.setRentValue(1200.0);
        responseDto.setPropertyType("Kitnet");
        responseDto.setOwnerConfirmation(true);
        responseDto.setTermsAgreement(true);

        when(propertyService.create(any())).thenReturn(responseDto);

        mockMvc.perform(post("/properties")
                        .with(user(currentUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.adTitle").value("Nice Kitnet"));
    }
}