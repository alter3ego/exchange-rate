package com.exchange.rate.model.service;

import com.exchange.rate.model.entity.CurrencyRate;

public interface CurrencyRateCache {
    CurrencyRate getCachedCurrentRates();

    CurrencyRate getCachedYesterdayRates();
}
