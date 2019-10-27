package com.altimetrik.favoriteartist.controller;

import com.altimetrik.favoriteartist.configuration.ExternalApplication;
import com.altimetrik.favoriteartist.model.ArtistInformation;
import com.altimetrik.favoriteartist.model.TrackInformation;
import com.altimetrik.favoriteartist.sytemclients.ILastFm;
import com.altimetrik.favoriteartist.sytemclients.IMusicMatch;
import com.altimetrik.favoriteartist.sytemclients.model.Artist;
import com.altimetrik.favoriteartist.sytemclients.model.Lyric;
import com.altimetrik.favoriteartist.sytemclients.model.SongInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/favorite-artist")
public class FavoriteArtistController {

    @Autowired
    private ILastFm iLastFm;
    @Autowired
    private IMusicMatch iMusicMatch;

    @GetMapping(value = "/api/artist/{country}/info")
    public TrackInformation getFavoriteArtist(@PathVariable String country) {
        SongInformation songInformation = iLastFm.getSongInformation(country);
        ArtistInformation artistInformation = iLastFm.getArtistInformation(songInformation.getArtistName());
        TrackInformation trackInformation = new TrackInformation();
        trackInformation.setArtistInformation(artistInformation);
        trackInformation.setDuration(songInformation.getDuration());
        trackInformation.setLyrics(iMusicMatch.getLyrics(songInformation.getTrackName(), artistInformation.getArtistName()));
        trackInformation.setTrackName(songInformation.getTrackName());
        return trackInformation;

    }
}
