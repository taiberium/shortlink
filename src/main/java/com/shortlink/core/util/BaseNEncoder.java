package com.shortlink.core.util;


import java.math.BigDecimal;

/**
 * Separate N Encoder for reusing purposes
 */
public class BaseNEncoder {

    private final String encodingString;
    private final char[] allowedCharacters;
    private final int baseSize;

    public BaseNEncoder(String encodingString) {
        this.encodingString = encodingString;
        this.allowedCharacters = encodingString.toCharArray();
        this.baseSize = allowedCharacters.length;
    }

    public long decode(String input) {
        char[] characters = input.toCharArray();
        int length = input.length();

        BigDecimal decodedBig = BigDecimal.ZERO;
        var counter = 0;
        for (char character : characters) {
            counter++;
            BigDecimal mapped = BigDecimal.valueOf(encodingString.indexOf(character));
            BigDecimal powResult = BigDecimal.valueOf(baseSize).pow(length - counter);
            decodedBig = decodedBig.add(powResult.multiply(mapped));
        }
        return decodedBig.longValue();
    }

    public String encode(long input) {
        StringBuilder encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            int charIndex = (int) (input % baseSize);
            char encodedChar = allowedCharacters[charIndex];
            encodedString.append(encodedChar);
            input = input / baseSize;
        }

        return encodedString.reverse().toString();
    }
}
