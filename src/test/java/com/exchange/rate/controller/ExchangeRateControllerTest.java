package com.exchange.rate.controller;

import com.exchange.rate.model.service.impl.GifServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRateController.class)
class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    byte[] expected = new byte[]{123, 28, 23, 123, 15, 34};

    @Autowired
    ExchangeRateController controller;

    @MockBean
    GifServiceImpl gitService;

    @Test
    void shouldReturnHttpStatus200AndExpectedByteArray() throws Exception {
        Mockito.when(gitService.getGifByCurrency(Mockito.anyString())).thenReturn(expected);
        mockMvc
                .perform(get("/api/v1/currency/UAH"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(expected));
    }
}