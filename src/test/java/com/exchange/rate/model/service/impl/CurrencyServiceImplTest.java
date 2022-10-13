package com.exchange.rate.model.service.impl;

import com.exchange.rate.client.CurrencyRateClient;
import com.exchange.rate.model.entity.CurrencyRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CurrencyServiceImplTest")
class CurrencyServiceImplTest {

    @InjectMocks
    CurrencyServiceImpl currencyService;
    @Mock
    CurrencyRateClient currencyProxy;

    CurrencyRate expectedForYesterday = generateCurrencyRate(20.0);
    CurrencyRate expectedForCurrent = generateCurrencyRate(10.0);

    @Test
    @DisplayName("should return current rates")
    void shouldReturnCurrentRates() {
        when(currencyProxy.getCurrentRates()).thenReturn(expectedForCurrent);

        CurrencyRate actualRates = currencyService.getCurrentRates();

        assertEquals(expectedForCurrent, actualRates);
    }

    @Test
    @DisplayName("should return yesterday rates")
    void shouldReturnYesterdayRates() {
        when(currencyProxy.getRatesForDate(anyString())).thenReturn(expectedForYesterday);

        CurrencyRate actualRates = currencyService.getYesterdayRates();

        assertEquals(expectedForYesterday, actualRates);
    }

    private CurrencyRate generateCurrencyRate(Double currencyValue) {
        Map<String, Double> rateMap = new HashMap<>();
        rateMap.put("USD", currencyValue);
        return new CurrencyRate(rateMap);
    }
}