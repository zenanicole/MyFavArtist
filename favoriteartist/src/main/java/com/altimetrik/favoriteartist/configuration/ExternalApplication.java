package com.altimetrik.favoriteartist.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "web")
public class ExternalApplication {
    private LastFm lastfm;
    private MusixMatch musixmatch;

    public LastFm getLastfm() {
        return lastfm;
    }

    public void setLastfm(LastFm lastfm) {
        this.lastfm = lastfm;
    }

    public MusixMatch getMusixmatch() {
        return musixmatch;
    }

    public void setMusixmatch(MusixMatch musixmatch) {
        this.musixmatch = musixmatch;
    }

    public static class LastFm {
        private String url;
        private String apiKey;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }

    public static class MusixMatch {
        private String url;
        private String apiKey;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
