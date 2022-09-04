package com.exchange.rate.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gif-service", url = "${base.gif.service.url}")
public interface GifClient {

    @GetMapping("/{id}.gif")
    byte[] getGifById(@PathVariable String id);
}
