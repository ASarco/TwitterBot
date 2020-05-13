package com.sarcobjects;

import twitter4j.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TowerBridge {

    static final List<String> TEXTS_LIST_EN = List.of(
            "That is Tower Bridge on the picture, not London Bridge.",
            "Tower Bridge is on the picture, not London Bridge.",
            "That is not London Bridge, but Tower Bridge.");
    static final List<String> TEXTS_LIST_ES = List.of(
            "La foto es de Tower Bridge, no de London Bridge.",
            "En esa foto aparece Tower Bridge, y no London Bridge.",
            "Es Tower Bridge el de la foto, no London Bridge.",
            "Ese no es London Bridge, sino Tower Bridge.");

    public static void main(String[] args) throws IOException {

        TowerBridge towerBridge = new TowerBridge();
        towerBridge.start();

    }

    private void start() {
        TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
        Twitter twitter = TwitterFactory.getSingleton();

        Map<String, CircularBuffer> statuses = Map.of(
                "en", new CircularBuffer(TEXTS_LIST_EN),
                "es", new CircularBuffer(TEXTS_LIST_ES));

        twitterStream.addListener(new TowerBridgeListener(twitter, statuses));
        FilterQuery filterQuery = new FilterQuery()
                .language(String.join(",", statuses.keySet()))
                .track("london bridge,londonbridge,puente londres,puentedelondres");
        twitterStream.filter(filterQuery);
    }
}
