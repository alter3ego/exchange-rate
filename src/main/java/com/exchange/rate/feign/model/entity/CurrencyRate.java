package com.exchange.rate.feign.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRate {
    private Map<String, Double> rates;
}
