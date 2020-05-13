package com.sarcobjects;

import twitter4j.*;

import java.io.IOException;
import java.util.List;

public class AAesAmerican {

    static final List<String> TEXTS_LIST = List.of(
            "AA es American Airlines, AR es Aerolíneas Argentinas",
            "AR es Aerolíneas Argentinas, AA es American Airlines",
            "American Airlines es AA, Aerolíneas Argentinas es AR",
            "Aerolíneas Argentinas es AR, American Airlines es AA",
            "Código de Aerolíneas Argentinas: AR, Código de American Airlines: AA",
            "Código de American Airlines: AA, Código de Aerolíneas Argentinas: AR"
    );

    public static void main(String[] args) throws IOException {

        AAesAmerican aAesAmerican = new AAesAmerican();
        aAesAmerican.start();

    }

    private void start() throws IOException {
        TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
        Twitter twitter = TwitterFactory.getSingleton();

        CircularBuffer circularBuffer = new CircularBuffer(TEXTS_LIST);

        twitterStream.addListener(new AmericanListener(twitter, circularBuffer));
        FilterQuery filterQuery = new FilterQuery().language("es").track("AA aerolineas,AA aerolíneas");
        twitterStream.filter(filterQuery);
    }
}
