package com.exchange.rate.feign.model.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class StringValidator {

    public boolean checkString(String checkedString, List<String> actualList) {
        System.out.println("ACT:" + actualList );
        return checkedString != null && actualList.contains(checkedString);
    }
}
