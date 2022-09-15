package com.exchange.rate.feign.controller;

import com.exchange.rate.feign.model.service.impl.GifServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExchangeRateControllerTest {

    byte[] expected = new byte[]{123, 28, 23, 123, 15, 34};

    @InjectMocks
    ExchangeRateController controller;
    @Mock
    GifServiceImpl gitService;

    @Test
    void getGifRateTest() {
        Mockito.when(gitService.getGifByCurrency(Mockito.anyString())).thenReturn(expected);

        byte[] actual = controller.getGifRate("USD");

        assertThat(actual).isEqualTo(expected);
    }
}