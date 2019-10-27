package com.altimetrik.favoriteartist.sytemclients;

import com.altimetrik.favoriteartist.sytemclients.model.Lyric;

public interface IMusicMatch {

    Lyric getLyrics (String trackName, String artistName);
}
