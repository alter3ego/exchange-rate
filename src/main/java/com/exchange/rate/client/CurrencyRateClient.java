package com.exchange.rate.client;

import com.exchange.rate.model.entity.CurrencyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-service", url = "${base.exchange.service.url}")
public interface CurrencyRateClient {

    @GetMapping("/latest.json?app_id=${openexchangerates.api.key}")
    CurrencyRate getCurrentRates();

    @GetMapping("/historical/{date}.json?app_id=${openexchangerates.api.key}")
    CurrencyRate getRatesForDate(@PathVariable String date);
}
