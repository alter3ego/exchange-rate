package com.exchange.rate.feign.model.service.impl;

import com.exchange.rate.feign.client.CurrencyRateClient;
import com.exchange.rate.feign.client.GifApiClient;
import com.exchange.rate.feign.client.GifClient;
import com.exchange.rate.feign.model.entity.CurrencyRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GifServiceImplTest {

    byte[] expected = new byte[]{123, 28, 23, 123, 15, 34};

    @InjectMocks
    GifServiceImpl gitService;
    @Mock
    CurrencyRateClient currencyProxy;
    @Mock
    GifApiClient gifApiProxy;
    @Mock
    GifClient gifProxy;
    @Mock
    StringValidator stringValidator;

    @Test
    void getGifByCurrencyTest() {
        when(stringValidator.checkString(anyString(), any())).thenReturn(true);

        Map<String, Double> rateMap = new HashMap<>();
        rateMap.put("USD", 12.0);
        CurrencyRate currencyRate = new CurrencyRate(rateMap);
        when(currencyProxy.getCurrentRates()).thenReturn(currencyRate);
        when(currencyProxy.getRatesForDate(anyString())).thenReturn(currencyRate);

        Map<String, String> currency = new HashMap<>();
        currency.put("id", "23");
        Map<String, Object> gifJson = new HashMap<>();
        gifJson.put("data", currency);
        when(gifApiProxy.getGifByTag(anyString())).thenReturn(gifJson);
        when(gifProxy.getGifById(anyString())).thenReturn(expected);

        byte[] actual = gitService.getGifByCurrency("USD");

        assertEquals(actual, expected);
    }
}