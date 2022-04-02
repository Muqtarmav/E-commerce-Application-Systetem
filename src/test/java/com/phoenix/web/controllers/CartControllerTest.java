package com.phoenix.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.data.dto.CartRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class CartControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("add a new item to ccart")
    void addItemToCart() throws Exception {


        ObjectMapper mapper = new ObjectMapper();
        CartRequestDto requestDto = new  CartRequestDto();
        requestDto.setProductId(13L);
        requestDto.setQuantity(1);
        requestDto.setUserId(5010L);

        mockMvc.perform(post("/api/cart")
                .contentType("application/json")
                .content(mapper.writeValueAsString(requestDto))).andExpect(status().is(200)).andDo(print());
    }
}