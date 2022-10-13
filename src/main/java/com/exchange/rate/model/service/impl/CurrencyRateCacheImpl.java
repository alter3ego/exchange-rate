package com.exchange.rate.model.service.impl;

import com.exchange.rate.model.entity.CurrencyRate;
import com.exchange.rate.model.service.CurrencyRateCache;
import com.exchange.rate.model.service.CurrencyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CurrencyRateCacheImpl implements CurrencyRateCache {
    private final CurrencyService currencyService;
    private CurrencyRate currentRates;
    private CurrencyRate yesterdayRates;

    public CurrencyRateCacheImpl(CurrencyService currencyService) {
        this.currencyService = currencyService;
        this.currentRates = requestCurrentRates();
        this.yesterdayRates = requestYesterdayRates();
    }

    @Override
    public CurrencyRate getCachedCurrentRates() {
        return currentRates;
    }

    @Override
    public CurrencyRate getCachedYesterdayRates() {
        return yesterdayRates;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void updateCurrentRates() {
        currentRates = requestCurrentRates();
        System.out.println("updateCurrentRates");
    }

    @Scheduled(cron = "1 0 0 * * ?")
    private void updateYesterdayRatesRates() {
        yesterdayRates = requestYesterdayRates();
        System.out.println("updateYesterdayRatesRates");
    }

    private CurrencyRate requestCurrentRates() {
        return currencyService.getCurrentRates();
    }

    private CurrencyRate requestYesterdayRates() {
        return currencyService.getYesterdayRates();
    }
}
