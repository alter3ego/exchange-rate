package com.exchange.rate.feign.model.service.impl;

import com.exchange.rate.feign.model.service.CurrencyValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CurrencyValidatorImpl implements CurrencyValidator {
    @Value("${currencies}")
    private String[] currencies;

    public boolean checkCurrency(String CheckedCurrency) {
        for (String currency : currencies) {
            if (currency.equals(CheckedCurrency)) {
                return true;
            }
        }
        return false;
    }
}
