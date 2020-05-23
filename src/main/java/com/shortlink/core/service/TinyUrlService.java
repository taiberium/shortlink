package com.shortlink.core.service;

import com.hazelcast.flakeidgen.FlakeIdGenerator;
import com.shortlink.core.model.UrlPair;
import com.shortlink.core.repository.UrlPairRepository;
import com.shortlink.core.util.IdConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TinyUrlService {

    private final UrlPairRepository urlPairRepository;
    private final FlakeIdGenerator flakeIdGenerator; // hazelcast always unique in cluster based on timestamp and nodeId generator

    public String findTinyUrl(String url) {
        UrlPair urlPair = urlPairRepository.findByUrl(url)
                .orElseGet(() -> createTinyToFullUrl(url));
        log.debug("TinyUrl: " + urlPair);
        return urlPair.getTinyUrl();
    }

    private UrlPair createTinyToFullUrl(String url) {
        long id = flakeIdGenerator.newId();
        String tinyUrl = findTinyUrlById(id);
        UrlPair urlPairDb = UrlPair.builder()
                .id(id)
                .url(url)
                .tinyUrl(tinyUrl)
                .build();
        return urlPairRepository.save(urlPairDb);
    }

    private String findTinyUrlById(long id) {
        return IdConverter.encode(id);
    }

    private long findIdByTinyUrl(String tinyUrl) {
        return IdConverter.decode(tinyUrl);
    }

    public Optional<String> findExistedUrl(String tinyUrl) {
        long id = findIdByTinyUrl(tinyUrl);
        return urlPairRepository.findById(id).map(UrlPair::getUrl);
    }
}
