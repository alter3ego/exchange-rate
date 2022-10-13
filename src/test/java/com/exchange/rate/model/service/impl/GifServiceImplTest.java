package com.exchange.rate.model.service.impl;

import com.exchange.rate.client.GifApiClient;
import com.exchange.rate.client.GifClient;
import com.exchange.rate.model.entity.CurrencyRate;
import com.exchange.rate.model.service.CurrencyRateCache;
import com.exchange.rate.util.FileManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GifServiceImplTest")
class GifServiceImplTest {

    byte[] expectedForRich = new byte[]{123, 28, 23, 123, 15, 34};
    byte[] expectedForBroke = new byte[]{28, 28, 28, 28, 28, 28};
    byte[] expectedForIncorrect = new byte[]{1, 1, 1, 1, 1, 1};

    @InjectMocks
    GifServiceImpl gitService;
    @Mock
    FileManager fileManager;
    @Mock
    CurrencyRateCache currencyRateCache;
    @Mock
    GifApiClient gifApiProxy;
    @Mock
    GifClient gifProxy;
    @Mock
    CurrencyValidator currencyValidator;

    @Test
    @DisplayName("should return expected for incorrect gif when validation failed")
    void shouldReturnExpectedForIncorrectGifWhenValidationFailed() {
        when(currencyValidator.checkString(anyString())).thenReturn(false);
        when(fileManager.readFileAsByteArray(anyString())).thenReturn(expectedForIncorrect);

        byte[] actual = gitService.getGifByCurrency("USD");

        assertArrayEquals(expectedForIncorrect, actual);
    }

    @Test
    @DisplayName("should return rich gif when rate increased")
    void shouldReturnRichGifWhenRateIncreased() {
        when(currencyValidator.checkString(anyString())).thenReturn(true);
        CurrencyRate todayCurrencyRate = createCurrencyRate(13.0);
        CurrencyRate yesterdayCurrencyRate = createCurrencyRate(12.0);

        when(currencyRateCache.getCachedCurrentRates()).thenReturn(todayCurrencyRate);
        when(currencyRateCache.getCachedYesterdayRates()).thenReturn(yesterdayCurrencyRate);


        Map<String, Object> gifJson = createGifJson("11");
        when(gifApiProxy.getGifByTag("broke")).thenReturn(gifJson);

        when(gifProxy.getGifById("11")).thenReturn(expectedForBroke);

        byte[] actual = gitService.getGifByCurrency("USD");

        assertArrayEquals(expectedForBroke, actual);
    }

    @Test
    @DisplayName("should return rich gif when rate decreased")
    void shouldReturnRichGifWhenRateDecreased() {
        when(currencyValidator.checkString(anyString())).thenReturn(true);

        CurrencyRate todayCurrencyRate = createCurrencyRate(11.0);
        CurrencyRate yesterdayCurrencyRate = createCurrencyRate(12.0);

        when(currencyRateCache.getCachedCurrentRates()).thenReturn(todayCurrencyRate);
        when(currencyRateCache.getCachedYesterdayRates()).thenReturn(yesterdayCurrencyRate);

        Map<String, Object> gifJson = createGifJson("11");
        when(gifApiProxy.getGifByTag("rich")).thenReturn(gifJson);

        when(gifProxy.getGifById("11")).thenReturn(expectedForRich);

        byte[] actual = gitService.getGifByCurrency("USD");

        assertArrayEquals(expectedForRich, actual);
    }

    private CurrencyRate createCurrencyRate(Double currencyValue) {
        Map<String, Double> rateMap = new HashMap<>();
        rateMap.put("USD", currencyValue);
        return new CurrencyRate(rateMap);
    }

    private Map<String, Object> createGifJson(String gifId) {
        Map<String, String> gif = new HashMap<>();
        gif.put("id", gifId);
        Map<String, Object> gifJson = new HashMap<>();
        gifJson.put("data", gif);
        return gifJson;
    }
}