package com.altimetrik.favoriteartist.sytemclients.implemantation;

import com.altimetrik.favoriteartist.configuration.ExternalApplication;
import com.altimetrik.favoriteartist.sytemclients.IMusicMatch;
import com.altimetrik.favoriteartist.sytemclients.model.Lyric;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.Instant;

@Component
public class MusicMatchImpl implements IMusicMatch {
    @Autowired
    private RestTemplate restTemplate;
    private static Logger log = LoggerFactory.getLogger(LastFmImpl.class);

    private ObjectMapper objectMapper;

    @Autowired
    private ExternalApplication externalApplication;

    @Autowired
    public MusicMatchImpl(RestTemplate restTemplate, ObjectMapper objectMapper, ExternalApplication externalApplication) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.externalApplication = externalApplication;
    }

    @Override
    public Lyric getLyrics(String trackName, String artistName) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(externalApplication.getMusixmatch().getUrl())
                .queryParam("apikey", externalApplication.getMusixmatch().getApiKey())
                .queryParam("q_track", trackName)
                .queryParam("q_artist", artistName);
        ResponseEntity<String> responseEntity = null;
        Instant startTime = Instant.now();
        responseEntity = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), String.class);
        Instant endTime = Instant.now();
        log.info("[completed] getSongInformation {}", Duration.between(startTime, endTime).toMillis());
        Lyric lyric = null;
        if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            String responseBody = responseEntity.getBody();
            try {
                JsonNode node = objectMapper.readTree(responseBody);
                JsonNode lyricNode = node.path("message").path("body").path("lyrics").path("lyrics_body");
                if (lyricNode != null) {
                    lyric = new Lyric();
                    lyric.setLyricBody(lyricNode.asText());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lyric;

    }
}
