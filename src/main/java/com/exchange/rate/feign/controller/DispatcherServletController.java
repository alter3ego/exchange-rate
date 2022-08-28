package com.exchange.rate.feign.controller;

import com.exchange.rate.feign.service.CurrencyExchangeServiceProxy;
import com.exchange.rate.feign.service.GifServiceProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Controller
public class DispatcherServletController {
    private final CurrencyExchangeServiceProxy currencyProxy;
    private final GifServiceProxy gifProxy;

    public DispatcherServletController(CurrencyExchangeServiceProxy currencyProxy, GifServiceProxy gifProxy) {
        this.currencyProxy = currencyProxy;
        this.gifProxy = gifProxy;
    }

    @GetMapping("/{currency}")
    public String getGifRate(@PathVariable String currency) {
        Integer different = currencyDifference(currency);

        Map<String, Object> gifJson;
        if (different >= 0) {
            gifJson = gifProxy.getGifByTag("broke");
        } else {
            gifJson = gifProxy.getGifByTag("rich");
        }

        Map<String, String> gifData = (Map<String, String>) gifJson.get("data");
        String gifUrl = gifData.get("embed_url");

        return "redirect:" + gifUrl;
    }

    private Integer currencyDifference(String currency) {
        Map<String, Object> currentRates = currencyProxy.getCurrentRates();

        String yesterday = getYesterday();
        Map<String, Object> yesterdayRates = currencyProxy.getRatesForDate(yesterday);

        Integer currentRate = getRate(currentRates, currency);
        Integer yesterdayRate = getRate(yesterdayRates, currency);
        return currentRate - yesterdayRate;
    }

    private Integer getRate(Map<String, Object> currentRates, String currency) {
        Map<String, Integer> rates = (Map<String, Integer>) currentRates.get("rates");
        return rates.get(currency);
    }

    private String getYesterday() {
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        Date myDate = Date.from(yesterday);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(myDate);
    }
}
