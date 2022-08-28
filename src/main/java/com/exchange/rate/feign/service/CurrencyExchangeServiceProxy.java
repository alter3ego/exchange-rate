package com.exchange.rate.feign.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "exchange-service", url = "${base.exchange.service.url}")
public interface CurrencyExchangeServiceProxy {

    String CURRENT_RATES_PATH = "/" + "${current.rate.value}" + "${json.type}" + "?app_id=" + "${openexchangerates.api.key}";
    String ARCHIVE_RATES_PATH = "/" + "${archive.rate.value}" + "{date}" + "${json.type}" + "?app_id=" + "${openexchangerates.api.key}";

    @GetMapping(CURRENT_RATES_PATH)
    Map<String, Object> getCurrentRates();

    @GetMapping(ARCHIVE_RATES_PATH)
    Map<String, Object> getRatesForDate(@PathVariable String date);
}
