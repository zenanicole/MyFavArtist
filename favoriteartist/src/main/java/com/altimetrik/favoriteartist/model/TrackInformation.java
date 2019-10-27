package com.altimetrik.favoriteartist.model;

import com.altimetrik.favoriteartist.sytemclients.model.Lyric;

public class TrackInformation {
    private String trackName;
    private String duration;
    private Lyric lyrics;
    private ArtistInformation artistInformation;

    public TrackInformation(){}

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Lyric getLyrics() {
        return lyrics;
    }

    public void setLyrics(Lyric lyrics) {
        this.lyrics = lyrics;
    }

    public ArtistInformation getArtistInformation() {
        return artistInformation;
    }

    public void setArtistInformation(ArtistInformation artistInformation) {
        this.artistInformation = artistInformation;
    }
}
