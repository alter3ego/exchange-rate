package com.exchange.rate.model.service;

import com.exchange.rate.model.entity.CurrencyRate;

public interface CurrencyService {
    CurrencyRate getCurrentRates();

    CurrencyRate getYesterdayRates();
}
