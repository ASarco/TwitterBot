package com.sarcobjects;

import twitter4j.*;

import java.io.IOException;

public class AAesAmerican {

    public static void main(String[] args) throws IOException {

        AAesAmerican aAesAmerican = new AAesAmerican();
        aAesAmerican.start();

    }

    private void start() throws IOException {
        TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
        Twitter twitter = TwitterFactory.getSingleton();

        twitterStream.addListener(new Listener(twitter));
        FilterQuery filterQuery = new FilterQuery().language("es").track("AA aerolineas,AA aerolíneas");
        twitterStream.filter(filterQuery);
    }
}
