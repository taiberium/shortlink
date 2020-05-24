package com.shortlink.core.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class BaseNEncoderTest {

    private final BaseNEncoder baseNEncoder = new BaseNEncoder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

    @Test
    public void shouldReturnEncodedStringLesserLengthThanNum() {
        long num = 1_000_000_000L;

        String encodedString = baseNEncoder.encode(num);

        Assertions.assertThat(encodedString.length()).isLessThan(String.valueOf(num).length());
    }

    @Test
    public void shouldReturnDecodedNumEqualToSourceNum() {
        long sourceNum = 1000_000_000L;

        String encodedString = baseNEncoder.encode(sourceNum);
        long decodedNum = baseNEncoder.decode(encodedString);

        Assertions.assertThat(sourceNum).isEqualTo(decodedNum);
    }

    @Test
    public void shouldReturnDecodedNumEqualToExpected() {
        String inputString = "g8";
        long expectedNum = 1000L;

        long number = baseNEncoder.decode(inputString);

        Assertions.assertThat(number).isEqualTo(expectedNum);
    }

    @Test
    public void shouldReturnEncodeStringEqualToExpected() {
        String expectedString = "g8";
        long num = 1000L;

        String encodedString = baseNEncoder.encode(num);

        Assertions.assertThat(expectedString).isEqualTo(encodedString);
    }

    @Test
    public void shouldEncodeAndDecodeAlmostMaxLongValue() {
        long num = Long.MAX_VALUE - 1_000;

        String encodedString = baseNEncoder.encode(num);
        long result = baseNEncoder.decode(encodedString);

        Assertions.assertThat(num).isEqualTo(result);
    }

    @Test
    public void shouldDecodeAlmostMaxLongValue() {
        String inputString = "aZl8N0y58vZ";

        long result = baseNEncoder.decode(inputString);

        Assertions.assertThat(result).isEqualTo(Long.MAX_VALUE - 1_000);
    }
}
