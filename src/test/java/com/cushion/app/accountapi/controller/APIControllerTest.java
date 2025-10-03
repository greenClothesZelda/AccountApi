package com.cushion.app.accountapi.controller;

// src/test/java/com/cushion/app/accountapi/controller/APIControllerTest.java
import com.cushion.app.accountapi.model.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginAndLogout() throws Exception {
        // 1. 로그인 요청
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("jinsu");
        loginDTO.setPassword("123");

        String response = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // 2. uuid 추출
        String uuid = response.replace("\"", ""); // UUID는 JSON 문자열로 반환됨

        // 3. 로그아웃 요청
        mockMvc.perform(post("/api/logout")
                        .header("Authorization", uuid))
                .andExpect(status().isOk());
    }
}
