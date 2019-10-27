package com.altimetrik.favoriteartist.sytemclients.implemantation;

import com.altimetrik.favoriteartist.configuration.ExternalApplication;
import com.altimetrik.favoriteartist.sytemclients.model.SongInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class LastFmImplTest {
    @Mock
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private ExternalApplication externalApplication;
    @InjectMocks
    private LastFmImpl subject;
    private String testCountry = "spain";
    private UriComponentsBuilder uriComponentsBuilder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        externalApplication = new ExternalApplication();
        externalApplication.setLastfm(new ExternalApplication.LastFm());
        externalApplication.getLastfm().setUrl("http://ws.audioscrobbler.com/2.0/");
        externalApplication.getLastfm().setApiKey("8750534ffaeb752d052eeafe0281cb70");

        uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(externalApplication.getLastfm().getUrl())
                .queryParam("api_key", externalApplication.getLastfm().getApiKey())
                .queryParam("country", testCountry)
                .queryParam("format", "json")
                .queryParam("method", "geo.gettoptracks");

        subject = new LastFmImpl(restTemplate, objectMapper, externalApplication);
    }

    @Test
    void getSongInformation() throws Exception{
        SongInformation expected = new SongInformation();
        expected.setDuration("239");
        expected.setArtistName("Radiohead");
        expected.setTrackName("Creep");
        ResponseEntity<String> expectedResponse= new
                ResponseEntity<>(generateResponse(),HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(
                "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=spain&api_key=8750534ffaeb752d052eeafe0281cb70&format=json",
                String.class)).thenReturn(expectedResponse);
        SongInformation actual = subject.getSongInformation(testCountry);
        assertEquals(expected, actual);
    }

    @Test
    void getArtistInformation() {
    }

    private String generateResponse() {
        return "{\n" +
                "  \"tracks\": {\n" +
                "    \"track\": [\n" +
                "      {\n" +
                "        \"name\": \"Creep\",\n" +
                "        \"duration\": \"239\",\n" +
                "        \"listeners\": \"1675468\",\n" +
                "        \"mbid\": \"d11fcceb-dfc5-4d19-b45d-f4e8f6d3eaa6\",\n" +
                "        \"url\": \"https://www.last.fm/music/Radiohead/_/Creep\",\n" +
                "        \"streamable\": {\n" +
                "          \"#text\": \"0\",\n" +
                "          \"fulltrack\": \"0\"\n" +
                "        },\n" +
                "        \"artist\": {\n" +
                "          \"name\": \"Radiohead\",\n" +
                "          \"mbid\": \"a74b1b7f-71a5-4011-9441-d0b5e4122711\",\n" +
                "          \"url\": \"https://www.last.fm/music/Radiohead\"\n" +
                "        },\n" +
                "        \"image\": [\n" +
                "          {\n" +
                "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                "            \"size\": \"small\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                "            \"size\": \"medium\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                "            \"size\": \"large\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                "            \"size\": \"extralarge\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"@attr\": {\n" +
                "          \"rank\": \"0\"\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"@attr\": {\n" +
                "      \"country\": \"Spain\",\n" +
                "      \"page\": \"1\",\n" +
                "      \"perPage\": \"50\",\n" +
                "      \"totalPages\": \"130703\",\n" +
                "      \"total\": \"6535128\"\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
    }
}
