package com.exchange.rate.model.service.impl;

import com.exchange.rate.client.GifApiClient;
import com.exchange.rate.client.GifClient;
import com.exchange.rate.model.entity.CurrencyRate;
import com.exchange.rate.model.service.CurrencyService;
import com.exchange.rate.model.service.GifService;
import com.exchange.rate.util.FileManager;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GifServiceImpl implements GifService {
    private static final String WRONG_GIF = "static/wrong.gif";
    private final FileManager fileManager;
    private final GifApiClient gifApiProxy;
    private final GifClient gifProxy;
    private final CurrencyValidator currencyValidator;
    private final CurrencyService currencyService;
    private CurrencyRate currentRates;
    private CurrencyRate yesterdayRates;

    public GifServiceImpl(FileManager fileManager, GifApiClient gifApiProxy, GifClient gifProxy,
                          CurrencyValidator currencyValidator, CurrencyService currencyService) {
        this.fileManager = fileManager;
        this.gifApiProxy = gifApiProxy;
        this.gifProxy = gifProxy;
        this.currencyValidator = currencyValidator;
        this.currencyService = currencyService;
        currentRates = currencyService.getCurrentRates();
        yesterdayRates = currencyService.getYesterdayRates();
    }

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
        Double currentRate = currentRates.getRates().get(currency);
        Double yesterdayRate = yesterdayRates.getRates().get(currency);

        return currentRate - yesterdayRate;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void updateCurrentRates() {
        currentRates = currencyService.getCurrentRates();
        System.out.println("updateCurrentRates");
    }

    @Scheduled(cron = "1 0 0 * * ?")
    private void updateYesterdayRatesRates() {
        yesterdayRates = currencyService.getYesterdayRates();
        System.out.println("updateYesterdayRatesRates");
    }
}
