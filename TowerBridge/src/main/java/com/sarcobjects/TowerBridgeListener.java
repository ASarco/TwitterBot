package com.sarcobjects;

import twitter4j.Logger;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class TowerBridgeListener extends AbstractStatusListener {

    private static final Logger LOGGER = Logger.getLogger(TowerBridgeListener.class);


    static final String MY_USER_NAME = "TowerThis";

    private final Twitter twitter;
    private final Map<String, CircularBuffer> statuses;

    public TowerBridgeListener(Twitter twitter, Map<String, CircularBuffer> statuses) {
        this.twitter = twitter;
        this.statuses = statuses;
    }

    @Override
    public void onStatus(Status status) {
        try {
            if (!MY_USER_NAME.equalsIgnoreCase(status.getUser().getScreenName()) && !status.isRetweet()) {
                String text = status.getText();
                if (status.getQuotedStatus() != null) {
                    text += "|" + status.getQuotedStatus().getText();
                }
                LOGGER.info(format("From: %s Lang: %s Text: %s", status.getUser().getScreenName(), status.getLang(), text));
                List<String> images = Arrays.stream(status.getMediaEntities())
                        .filter(mediaEntity -> "photo".equals(mediaEntity.getType()))
                        .map(MediaEntity::getMediaURL)
                        .collect(Collectors.toList());
                LOGGER.info(format("Contains images %s %n",  images));
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
        String msg = statuses.get(status.getLang()).getNextText();
        LOGGER.info("REPLYING =====> " + msg);

    }
}
