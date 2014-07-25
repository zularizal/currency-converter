package org.apilytic.currency.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConvertRateFormatTest {

    private ConvertRateFormat convertRateFormat;

    @BeforeClass
    public void init() {
        convertRateFormat = new ConvertRateFormat();
    }

    @Test
    public void cleanNumberOfInteger() {
        assertThat(convertRateFormat.cleanNumber("100"), equalTo("100"));
    }

    @Test
    public void cleanNumberOfDecimal() {
        assertThat(convertRateFormat.cleanNumber("100.12"), equalTo("100.12"));
    }

    @Test
    public void cleanNumberOfDecimalUs() {
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        assertThat(convertRateFormat.cleanNumber(format.format(100.12)), equalTo("100.12"));
        assertThat(convertRateFormat.cleanNumber(format.format(100010100.12)), equalTo("100010100.12"));
    }
    
    @Test
    public void cleanNumberOfDecimalEuro() {
    	assertThat(convertRateFormat.cleanNumber("100.12EUR"), equalTo("100.12"));
    	assertThat(convertRateFormat.cleanNumber("100.12 EUR"), equalTo("100.12"));
    }

    @Test
    public void cleanNumberOfCommaSeparatedDecimals() {
        assertThat(convertRateFormat.cleanNumber("100,12"), equalTo("100.12"));
        assertThat(convertRateFormat.cleanNumber("1,00,12"), equalTo("100.12"));
    }

    @Test
    public void cleanNumberOfLargeInteger() {
        assertThat(convertRateFormat.cleanNumber("1001210012"), equalTo("1001210012"));
        assertThat(convertRateFormat.cleanNumber("10012 100 12"), equalTo("1001210012"));
    }
}