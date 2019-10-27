package com.altimetrik.favoriteartist.sytemclients.implemantation;

import com.altimetrik.favoriteartist.configuration.ExternalApplication;
import com.altimetrik.favoriteartist.model.ArtistInformation;
import com.altimetrik.favoriteartist.sytemclients.ILastFm;
import com.altimetrik.favoriteartist.sytemclients.model.SongInformation;
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
import java.util.Iterator;

@Component
public class LastFmImpl implements ILastFm {
    private static Logger log = LoggerFactory.getLogger(LastFmImpl.class);
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private ExternalApplication externalApplication;

    @Autowired
    public LastFmImpl(RestTemplate restTemplate, ObjectMapper objectMapper, ExternalApplication externalApplication){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.externalApplication = externalApplication;
    }
    @Override
    public SongInformation getSongInformation(String country) {
        log.info("[started] getSongInformation");

        SongInformation song = null;
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(externalApplication.getLastfm().getUrl())
                .queryParam("api_key", externalApplication.getLastfm().getApiKey())
                .queryParam("country", country)
                .queryParam("format", "json")
                .queryParam("method", "geo.gettoptracks");

        Instant startTime = Instant.now();
        ResponseEntity<String> response = restTemplate.getForEntity(
                uriComponentsBuilder.toUriString(),
                String.class
        );

        Instant endTime = Instant.now();
        log.info("[completed] getSongInformation {}",Duration.between(startTime, endTime).toMillis());

        if(response.getStatusCode()== HttpStatus.OK){
            String responseBody = response.getBody();
            try {
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode tracks = rootNode.path("tracks").path("track");
                if(tracks.isArray()){
                    Iterator<JsonNode> nodes = tracks.elements();
                    JsonNode topTrack = nodes.next();
                    song = new SongInformation();
                    song.setTrackName(topTrack.path("name").asText());
                    song.setArtistName(topTrack.path("artist").path("name").asText());
                    song.setDuration(topTrack.path("duration").asText());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        log.info("[completed] getSongInformation completed building SongInformation object");
        return song;
    }

    @Override
    public ArtistInformation getArtistInformation(String artistName) {
        SongInformation song = null;
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(externalApplication.getLastfm().getUrl())
                .queryParam("api_key", externalApplication.getLastfm().getApiKey())
                .queryParam("artist", artistName)
                .queryParam("format", "json")
                .queryParam("method", "artist.getinfo");

        Instant startTime = Instant.now();
        ResponseEntity<String> response = restTemplate.getForEntity(
                uriComponentsBuilder.toUriString(),
                String.class
        );

        Instant endTime = Instant.now();
             log.info("[completed] getArtistInfo {}",Duration.between(startTime, endTime).toMillis());
        ArtistInformation artistInformation=null;
        if(response.getStatusCode()== HttpStatus.OK){
            String responseBody = response.getBody();
            try {
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode artist = rootNode.path("artist");
                if(artistName!=null){
                    JsonNode artistUrl=rootNode.path("artist").path("image");
                    if(artistUrl.isArray()) {
                        Iterator<JsonNode> nodes = artistUrl.elements();
                        JsonNode nodesImage = nodes.next();
                        artistInformation = new ArtistInformation(artist.path("name").asText(), nodesImage.path("#text").asText());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        log.info("[completed] getArtistInfo completed building SongInformation object");
        return artistInformation;
    }
}
