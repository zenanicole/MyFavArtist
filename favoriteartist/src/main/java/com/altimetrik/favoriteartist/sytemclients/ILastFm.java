package com.altimetrik.favoriteartist.sytemclients;

import com.altimetrik.favoriteartist.model.ArtistInformation;
import com.altimetrik.favoriteartist.sytemclients.model.SongInformation;
import org.springframework.boot.autoconfigure.data.mongo.ReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor;

public interface ILastFm {

    SongInformation getSongInformation(String country);

    ArtistInformation getArtistInformation (String artistName);

}
