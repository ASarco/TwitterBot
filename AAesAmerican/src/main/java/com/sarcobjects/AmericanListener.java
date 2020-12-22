package com.sarcobjects;

import twitter4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

class AmericanListener extends AbstractStatusListener {

    private static final Logger LOGGER = Logger.getLogger(AmericanListener.class);

    static final String MY_USER_NAME = "aesamerican";
    static final String CATEGORY = "American";

    private final CircularBuffer statuses;
    //private final Categoriser categoriser;

    public AmericanListener(Twitter twitter, CircularBuffer statuses) throws IOException {
        super(twitter);
        this.statuses = statuses;
        //this.categoriser = new Categoriser("/es-doccat.bin");
    }

    @Override
    public void onStatus(Status status) {

        try {
            //Do not respond to my own tweets
            if (!MY_USER_NAME.equalsIgnoreCase(status.getUser().getScreenName()) && !status.isRetweet()) {
                String text = status.getText();
                if (!text.matches("AA(?i)")) {  //reply only if AA is in the main tweet
                    return;
                }
                if (nonNull(status.getQuotedStatus())) {
                    text += "|" + status.getQuotedStatus().getText();
                }
                if (text.contains(CATEGORY)) { //if the text contains American, then they probably talking about the right thing.
                    return;
                }
                LOGGER.info(format("%n%nFrom: %s ,Text: %s", status.getUser().getScreenName(), text));

                LOGGER.info("REPLYING =====>");
                reply(status, statuses.getNextText());
            }
        } catch (Exception e) {
            LOGGER.warn("Error receiving status", e);
        }
    }



}
