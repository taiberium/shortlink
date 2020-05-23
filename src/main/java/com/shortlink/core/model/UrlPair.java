package com.shortlink.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.io.Serializable;

import static com.shortlink.core.config.HazelcastConfig.MAP_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@KeySpace(MAP_NAME)
public class UrlPair implements Serializable {

    private static final long serialVersionUID = 42L;

    @Id
    private Long id;
    private String url;
    private String tinyUrl;
}
