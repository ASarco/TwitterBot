package com.sarcobjects;

import twitter4j.Logger;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Listener extends AbstractStatusListener {

    private static final Logger LOGGER = Logger.getLogger(Listener.class);


    static final String MY_USER_NAME = "TowerThis";

    private final Twitter twitter;
    //private final CircularBuffer statuses;

    public Listener(Twitter twitter) {

        this.twitter = twitter;
    }

    @Override
    public void onStatus(Status status) {
        try {
            if (!MY_USER_NAME.equalsIgnoreCase(status.getUser().getScreenName()) && !status.isRetweet()) {
                String text = status.getText();
                if (status.getQuotedStatus() != null) {
                    text += "|" + status.getQuotedStatus().getText();
                }
                LOGGER.info(format("%n%nFrom: %s Lang: %s Text: %s", status.getUser().getScreenName(), status.getLang(), text));
                MediaEntity[] mediaEntities = status.getMediaEntities();
                List<String> images = Arrays.stream(mediaEntities)
                        .filter(mediaEntity -> "photo".equals(mediaEntity.getType()))
                        .map(MediaEntity::getMediaURL)
                        .collect(Collectors.toList());
                LOGGER.info("Contains images " + images);
                if (!images.isEmpty()) {
                    verifyImages(images);
                    reply(status);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Error receiving status", e);
        }
    }

    private void verifyImages(List<String> images) {
    }

    private void reply(Status status) {
        LOGGER.info("REPLYING =====>");

    }
}
