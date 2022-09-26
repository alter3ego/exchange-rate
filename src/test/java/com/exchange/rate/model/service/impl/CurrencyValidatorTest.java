package com.exchange.rate.model.service.impl;

import com.exchange.rate.ExchangeRateFeignApplication;
import com.exchange.rate.client.CurrencyRateClient;
import com.exchange.rate.model.entity.CurrencyRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CurrencyValidatorTest")
class CurrencyValidatorTest {
    @Mock
    CurrencyRateClient currencyProxy;

    CurrencyValidator currencyValidator;

    @BeforeEach
    void init() {
        when(currencyProxy.getCurrentRates()).thenReturn(currencyRateProvider());
        currencyValidator = new CurrencyValidator(currencyProxy);
    }

    @ParameterizedTest
    @DisplayName("should successfully pass validation for correct currency")
    @MethodSource("testCases")
    void shouldSuccessfullyPassValidationForCorrectCurrency(String desc, String checkedCurrency) {
        boolean actual = currencyValidator.checkString(checkedCurrency);
        assertTrue(actual);
    }

    @ParameterizedTest
    @DisplayName("should return false for null and empty strings")
    @NullAndEmptySource
    void shouldReturnFalseForForNullAndEmptyStrings(String input) {
        boolean actual = currencyValidator.checkString(input);
        assertFalse(actual);
    }

    @Test
    void shouldSuccessfullyComparedCurrenciesPropertyAndCurrencyList() {
        Properties prop = new Properties();
        try {
            prop.load(ExchangeRateFeignApplication.class.getClassLoader().getResourceAsStream("api.properties"));
            String expected = prop.getProperty("currencies");
            String currenciesList = currencyListProvider().toString();
            String actual = currenciesList.substring(1, currenciesList.length() - 1);
            
            assertEquals(expected, actual);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("correct currency UAH", "UAH"),
                Arguments.of("correct currency USD", "USD"),
                Arguments.of("correct currency HNL", "HNL"),
                Arguments.of("correct currency JPY", "JPY")
        );
    }

    private static CurrencyRate currencyRateProvider() {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setRates(currencyMapProvider(currencyListProvider()));
        return currencyRate;
    }

    private static Map<String, Double> currencyMapProvider(List<String> list) {
        return list.stream().collect(Collectors.toMap(s -> s, s -> 0.0));
    }

    private static List<String> currencyListProvider() {
        return List.of(
                "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT",
                "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BZD",
                "CAD", "CDF", "CHF", "CLF", "CLP", "CNH", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK",
                "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP",
                "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS",
                "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF",
                "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL",
                "MGA", "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD",
                "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG",
                "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL",
                "SOS", "SRD", "SSP", "STD", "STN", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP",
                "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VES", "VND", "VUV", "WST",
                "XAF", "XAG", "XAU", "XCD", "XDR", "XOF", "XPD", "XPF", "XPT", "YER", "ZAR", "ZMW", "ZWL"
        );
    }
}