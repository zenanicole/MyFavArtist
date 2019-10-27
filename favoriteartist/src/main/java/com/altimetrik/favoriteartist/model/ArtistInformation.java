package com.altimetrik.favoriteartist.model;

public class ArtistInformation {
    private String artistName;
    private String artistPhotoUrl;

    public ArtistInformation(String artistName, String artistPhotoUrl){
        this.artistName = artistName;
        this.artistPhotoUrl = artistPhotoUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistPhotoUrl() {
        return artistPhotoUrl;
    }

    public void setArtistPhotoUrl(String artistPhotoUrl) {
        this.artistPhotoUrl = artistPhotoUrl;
    }
}
