package com.shortlink.core.service;

import com.hazelcast.flakeidgen.FlakeIdGenerator;
import com.shortlink.core.model.UrlPair;
import com.shortlink.core.repository.UrlPairRepository;
import com.shortlink.core.util.IdConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class TinyUrlServiceTest {

    private final FlakeIdGenerator flakeIdGeneratorMock = Mockito.mock(FlakeIdGenerator.class);
    private final UrlPairRepository urlPairRepositoryMock = Mockito.mock(UrlPairRepository.class);
    private final TinyUrlService tinyUrlService = new TinyUrlService(urlPairRepositoryMock, flakeIdGeneratorMock);

    @Test
    public void should_notCreateNewDbRecord_whenFindTinyUrlwithExistedTinyUrlCalled() {
        String url = "http://example.ru";
        UrlPair urlPair = UrlPair.builder().id(123L).url(url).tinyUrl("tinyUrl").build();
        Mockito.when(urlPairRepositoryMock.findByUrl(url)).thenReturn(Optional.of(urlPair));

        tinyUrlService.findTinyUrl(url);

        Mockito.verify(urlPairRepositoryMock, Mockito.times(0)).save(Mockito.any(UrlPair.class));
    }

    @Test
    public void should_returnDbTinyUrl_whenFindTinyUrlwithExistedUrlCalled() {
        String url = "http://example.ru";
        String tinyUrl = "tinyUrl";
        UrlPair urlPair = UrlPair.builder().id(123L).url(url).tinyUrl(tinyUrl).build();
        Mockito.when(urlPairRepositoryMock.findByUrl(url)).thenReturn(Optional.of(urlPair));

        String resultTinyUrl = tinyUrlService.findTinyUrl(url);

        Assertions.assertThat(resultTinyUrl).isEqualTo(tinyUrl);
    }

    @Test
    public void should_createNewDbRecord_whenFindNewTinyUrlCalled() {
        String url = "http://example.ru";
        long generatedId = 316637605095538689L;
        String tinyUrl = IdConverter.encode(generatedId);
        UrlPair urlPair = UrlPair.builder().id(generatedId).url(url).tinyUrl(tinyUrl).build();
        Mockito.when(flakeIdGeneratorMock.newId()).thenReturn(generatedId);
        Mockito.when(urlPairRepositoryMock.findByUrl(url)).thenReturn(Optional.empty());
        Mockito.when(urlPairRepositoryMock.save(urlPair)).thenReturn(urlPair);

        tinyUrlService.findTinyUrl(url);

        Mockito.verify(urlPairRepositoryMock, Mockito.times(1)).save(urlPair);
    }

    @Test
    public void should_returnGeneratedTinyUrl_whenFindNewTinyUrlCalled() {
        String url = "http://example.ru";
        long generatedId = 316637605095538689L;
        String tinyUrl = IdConverter.encode(generatedId);
        UrlPair urlPair = UrlPair.builder().id(generatedId).url(url).tinyUrl(tinyUrl).build();
        Mockito.when(flakeIdGeneratorMock.newId()).thenReturn(generatedId);
        Mockito.when(urlPairRepositoryMock.findByUrl(url)).thenReturn(Optional.empty());
        Mockito.when(urlPairRepositoryMock.save(urlPair)).thenReturn(urlPair);

        String resultTinyUrl = tinyUrlService.findTinyUrl(url);

        Assertions.assertThat(resultTinyUrl).isEqualTo(tinyUrl);
    }
}
