package com.shortlink.core.repository;

import com.shortlink.core.model.UrlPair;
import org.springframework.data.hazelcast.repository.HazelcastRepository;

import java.util.Optional;

public interface UrlPairRepository extends HazelcastRepository<UrlPair, Long> {

    Optional<UrlPair> findByUrl(String url);
}