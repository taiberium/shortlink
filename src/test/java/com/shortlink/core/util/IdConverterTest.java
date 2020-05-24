package com.shortlink.core.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class IdConverterTest {

    @Test
    public void should_encodedAndDecodedValueBeTheSave_IfEncodeTypicalHazelcastClusterId() {
        long hazelCastId = 316637605095538689L;
        String encodedString = IdConverter.encode(hazelCastId);
        long decodedNum = IdConverter.decode(encodedString);

        Assertions.assertThat(decodedNum).isEqualTo(hazelCastId);
    }

    @Test
    public void should_encodedStringSizeBeLessOrEqualTo10_IfEncodeTypicalHazelcastClusterId(){
        long hazelCastId = 316637605095538689L;
        int maxEncodedStringLength = 10;

        String encodedString = IdConverter.encode(hazelCastId);

        Assertions.assertThat(encodedString.length()).isLessThanOrEqualTo(maxEncodedStringLength);
    }
}
