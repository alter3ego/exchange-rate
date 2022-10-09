package com.exchange.rate.model.service.impl;

import com.exchange.rate.client.CurrencyRateClient;
import com.exchange.rate.client.GifApiClient;
import com.exchange.rate.client.GifClient;
import com.exchange.rate.model.entity.CurrencyRate;
import com.exchange.rate.model.service.GifService;
import com.exchange.rate.util.FileManager;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Service
public class GifServiceImpl implements GifService {
    private static final String WRONG_GIF = "static/wrong.gif";
    private final FileManager fileManager;
    private final CurrencyRateClient currencyProxy;
    private final GifApiClient gifApiProxy;
    private final GifClient gifProxy;
    private final CurrencyValidator currencyValidator;

    @Override
    public byte[] getGifByCurrency(String currency) {
        if (!currencyValidator.checkString(currency)) {
            return fileManager.readFileAsByteArray(WRONG_GIF);
        }

        Double different = currencyDifference(currency);

        Map<String, Object> gifJson;
        if (different >= 0) {
            gifJson = gifApiProxy.getGifByTag("broke");
        } else {
            gifJson = gifApiProxy.getGifByTag("rich");
        }

        Map<String, String> gifData = (Map<String, String>) gifJson.get("data");
        String gifId = gifData.get("id");

        return gifProxy.getGifById(gifId);
    }

    private Double currencyDifference(String currency) {
        CurrencyRate currentRates = currencyProxy.getCurrentRates();
        String yesterday = getYesterday();
        CurrencyRate yesterdayRates = currencyProxy.getRatesForDate(yesterday);

        Double currentRate = currentRates.getRates().get(currency);
        Double yesterdayRate = yesterdayRates.getRates().get(currency);

        return currentRate - yesterdayRate;
    }

    private String getYesterday() {
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        Date myDate = Date.from(yesterday);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(myDate);
    }
}
