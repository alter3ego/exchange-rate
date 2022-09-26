package com.exchange.rate.controller;

import com.exchange.rate.model.service.GifService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ExchangeRateController {
    private GifService gifService;

    @GetMapping(value = "/api/v1/currency/{currency}", produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getGifRate(@PathVariable String currency) {
        return gifService.getGifByCurrency(currency);
    }
}
