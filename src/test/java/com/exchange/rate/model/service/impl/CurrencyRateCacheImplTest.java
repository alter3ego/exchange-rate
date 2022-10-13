package com.exchange.rate.model.service.impl;

import com.exchange.rate.model.entity.CurrencyRate;
import com.exchange.rate.model.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CurrencyRateCacheImplTest")
class CurrencyRateCacheImplTest {
    @Mock
    CurrencyService currencyService;

    CurrencyRateCacheImpl currencyRateCache;

    CurrencyRate expectedForCurrent = generateCurrencyRate(10.0);
    CurrencyRate expectedForYesterday = generateCurrencyRate(20.0);

    @BeforeEach
    void init() {
        when(currencyService.getCurrentRates()).thenReturn(expectedForCurrent);
        when(currencyService.getYesterdayRates()).thenReturn(expectedForYesterday);

        currencyRateCache = new CurrencyRateCacheImpl(currencyService);
    }

    @Test
    @DisplayName("should return cached current rates")
    void shouldReturnCachedCurrentRates() {
        CurrencyRate actualRates = currencyRateCache.getCachedCurrentRates();
        assertEquals(expectedForCurrent, actualRates);
    }

    @Test
    @DisplayName("should return cached yesterday rates")
    void shouldReturnCachedYesterdayRates() {
        CurrencyRate actualRates = currencyRateCache.getCachedYesterdayRates();
        assertEquals(expectedForYesterday, actualRates);
    }

    private CurrencyRate generateCurrencyRate(Double currencyValue) {
        Map<String, Double> rateMap = new HashMap<>();
        rateMap.put("USD", currencyValue);
        return new CurrencyRate(rateMap);
    }
}