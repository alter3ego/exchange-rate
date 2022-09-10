package com.exchange.rate.feign.model.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CurrencyValidator {
    @Value("${currencies}")
    private String[] currencies;

    public boolean checkCurrency(String checkedCurrency) {
        for (String currency : currencies) {
            if (currency.equals(checkedCurrency)) {
                return true;
            }
        }
        return false;
    }
}
