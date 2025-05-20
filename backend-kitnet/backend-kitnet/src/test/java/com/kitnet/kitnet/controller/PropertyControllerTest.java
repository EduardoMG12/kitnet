package com.kitnet.kitnet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitnet.kitnet.dto.PropertyRequestDto;
import com.kitnet.kitnet.dto.PropertyResponseDto;
import com.kitnet.kitnet.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

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

        mockMvc.perform(get("/properties"))
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.adTitle").value("Nice Kitnet"));
    }
}
