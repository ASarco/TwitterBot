package com.sarcobjects;

import twitter4j.*;

import java.io.IOException;

public class TowerBridge {

    public static void main(String[] args) throws IOException {

        TowerBridge towerBridge = new TowerBridge();
        towerBridge.start();

    }

    private void start() {
        TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
        Twitter twitter = TwitterFactory.getSingleton();

        twitterStream.addListener(new Listener(twitter));
        FilterQuery filterQuery = new FilterQuery().language("es,en").track("london bridge,londonbridge,puente londres,puentedelondres");
        twitterStream.filter(filterQuery);
    }
}
