package com.shortlink.core.repository;

import com.shortlink.core.CoreApplication;
import com.shortlink.core.model.UrlPair;
import com.shortlink.core.util.IdConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = {CoreApplication.class})
public class UrlPairRepositoryTest {

    @Autowired
    private UrlPairRepository urlPairRepository;

    @BeforeEach
    public void init() {
        urlPairRepository.deleteAll();
    }

    @Test
    public void should_returnUrlPair_whenDbContainRecordWithRequiredUrl() {
        String url = "http://example.ru";
        long generatedId = 316637605095538689L;
        String tinyUrl = IdConverter.encode(generatedId);
        UrlPair urlPair = UrlPair.builder().id(generatedId).url(url).tinyUrl(tinyUrl).build();
        urlPairRepository.save(urlPair);

        Optional<UrlPair> optionalDbUrlPair = urlPairRepository.findByUrl(url);

        Assertions.assertThat(optionalDbUrlPair.isPresent()).isTrue();
    }

    @Test
    public void should_notReturnUrlPair_whenDbContainRecordWithWrongUrl() {
        String searchUrl = "http://example.ru/search/1234";
        String wrongUrl = "http://example.ru";
        long generatedId = 316637605095538689L;
        String tinyUrl = IdConverter.encode(generatedId);
        UrlPair urlPair = UrlPair.builder().id(generatedId).url(wrongUrl).tinyUrl(tinyUrl).build();
        urlPairRepository.save(urlPair);

        Optional<UrlPair> optionalDbUrlPair = urlPairRepository.findByUrl(searchUrl);

        Assertions.assertThat(optionalDbUrlPair.isPresent()).isFalse();
    }
}
