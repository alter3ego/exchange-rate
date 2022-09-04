package com.exchange.rate.feign.model.service;

public interface GifService {
    byte[] getGifByCurrency(String currency);
}
