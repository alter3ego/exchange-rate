package com.exchange.rate.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "gif-api-service", url = "${base.api.gif.service.url}")
public interface GifApiClient {

    @GetMapping("?api_key=${giphy.api.key}&tag={tag}")
    Map<String, Object> getGifByTag(@PathVariable String tag);
}
