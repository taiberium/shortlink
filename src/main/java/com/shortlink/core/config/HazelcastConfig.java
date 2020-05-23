package com.shortlink.core.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hazelcast.repository.config.EnableHazelcastRepositories;

@Configuration
@EnableHazelcastRepositories(basePackages = {"com.shortlink.core.repository"})
public class HazelcastConfig {

    public static final String MAP_NAME = "TinyToFullUrl";

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config()
                .addMapConfig(
                        new MapConfig()
                                .setName(MAP_NAME)
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(86400)
                );

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public FlakeIdGenerator idGenerator() {
        return hazelcastInstance().getFlakeIdGenerator("id");
    }
}
