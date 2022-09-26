package com.exchange.rate.model.service.impl;

import com.exchange.rate.client.CurrencyRateClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrencyValidator {
    private final CurrencyRateClient currencyProxy;
    private final List<String> currencyList;

    public CurrencyValidator(CurrencyRateClient currencyProxy) {
        this.currencyProxy = currencyProxy;
        currencyList = buildCurrencyList();
    }

    public boolean checkString(String checkedCurrency) {
        return checkedCurrency != null && currencyList.contains(checkedCurrency);
    }

    private List<String> buildCurrencyList() {
        return currencyProxy.getCurrentRates()
                .getRates()
                .keySet()
                .stream()
                .toList();
    }
}
