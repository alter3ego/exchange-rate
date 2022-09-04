package com.exchange.rate.feign.model.service.impl;

import com.exchange.rate.feign.client.CurrencyRateClient;
import com.exchange.rate.feign.client.GifApiClient;
import com.exchange.rate.feign.client.GifClient;
import com.exchange.rate.feign.model.entity.CurrencyRate;
import com.exchange.rate.feign.model.exception.FileNotFoundException;
import com.exchange.rate.feign.model.service.CurrencyValidator;
import com.exchange.rate.feign.model.service.GifService;
import lombok.AllArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Service
public class GifServiceImpl implements GifService {
    private static final String WRONG_GIF = "static/wrong.gif";

    private final CurrencyRateClient currencyProxy;
    private final GifApiClient gifApiProxy;
    private final GifClient gifProxy;
    private final CurrencyValidator currencyValidator;

    @Override
    public byte[] getGifByCurrency(String currency) {
        if (!currencyValidator.checkCurrency(currency)) {
            return getWrongGif();
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

    private byte[] getWrongGif() {
        try {
            InputStream in = new ClassPathResource(WRONG_GIF).getInputStream();
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new FileNotFoundException("Access error to wrong.gif", e);
        }
    }
}
