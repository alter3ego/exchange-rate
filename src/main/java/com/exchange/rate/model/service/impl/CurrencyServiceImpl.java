package com.exchange.rate.model.service.impl;

import com.exchange.rate.client.CurrencyRateClient;
import com.exchange.rate.model.entity.CurrencyRate;
import com.exchange.rate.model.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRateClient currencyProxy;

    @Override
    public CurrencyRate getCurrentRates() {
        return currencyProxy.getCurrentRates();
    }

    @Override
    public CurrencyRate getYesterdayRates() {
        String yesterday = getYesterday();
        return currencyProxy.getRatesForDate(yesterday);
    }

    private String getYesterday() {
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        Date myDate = Date.from(yesterday);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(myDate);
    }
}
