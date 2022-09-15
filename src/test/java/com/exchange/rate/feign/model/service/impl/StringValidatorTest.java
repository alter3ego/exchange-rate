package com.exchange.rate.feign.model.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { StringValidator.class })
class StringValidatorTest {
    @Autowired
    private StringValidator stringValidator;

    private final List<String> currencies = new ArrayList<>();

    @ParameterizedTest
    @MethodSource("testCases")
    void checkCurrencyTest(String desc, Boolean expected, String checkedCurrency) {
        currencies.add("UAH");
        currencies.add("USD");

        Boolean actual = stringValidator.checkString(checkedCurrency,currencies);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("correct currency UAH", true, "UAH"),
                Arguments.of("correct currency USD", true, "USD"),
                Arguments.of("incorrect currency USD", false, "UUU"),
                Arguments.of("incorrect input", false, "")
        );
    }
}