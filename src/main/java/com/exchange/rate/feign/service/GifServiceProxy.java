package com.exchange.rate.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "gif-service", url = "${base.gif.service.url}")
public interface GifServiceProxy {

    String GIF_PATH = "?api_key=" + "${giphy.api.key}" + "&tag=" + "{tag}";

    @GetMapping(GIF_PATH)
    Map<String, Object> getGifByTag(@PathVariable String tag);
}
