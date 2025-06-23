package com.kitnet.kitnet.config;

import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import({SecurityConfig.class, SecurityConfigTest.TestMocksConfig.class})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestMocksConfig {

        @Bean
        public PropertyService propertyService() {
            return mock(PropertyService.class);
        }

        @Bean
        public ContactMessageService contactMessageService() {
            return mock(ContactMessageService.class);
        }

        @Bean
        public ContactMessageRepository contactMessageRepository() {
            return mock(ContactMessageRepository.class);
        }

        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }
    }

    @Test
    void testAllRequestsPermitted() throws Exception {
        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/any/other/path"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCsrfDisabledForPostRequests() throws Exception {
        mockMvc.perform(post("/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testPutRequestsPermitted() throws Exception {
        mockMvc.perform(put("/properties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteRequestsPermitted() throws Exception {
        mockMvc.perform(delete("/properties/1"))
                .andExpect(status().isOk());
    }
}
