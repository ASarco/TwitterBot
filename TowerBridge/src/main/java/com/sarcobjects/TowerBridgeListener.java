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

    private final Map<String, CircularBuffer> statuses;
    private final ImageIdentifier imageIdentifier;


    public TowerBridgeListener(Twitter twitter, Map<String, CircularBuffer> statuses, ImageIdentifier imageIdentifier) {
        super(twitter);
        this.statuses = statuses;
        this.imageIdentifier = imageIdentifier;
    }

    @Override
    public void onStatus(Status status) {
        try {
            if (MY_USER_NAME.equalsIgnoreCase(status.getUser().getScreenName()) || status.isRetweet()) {
                return;
            }
            String text = status.getText();
            if (status.getQuotedStatus() != null) {
                text += "|" + status.getQuotedStatus().getText();
            }
            LOGGER.info(format("FROM: @%s%n %s", status.getUser().getScreenName(), text));
            if (!TweetVerifier.shouldReply(text)) {
                LOGGER.info("NO REPLY");
                return;
            }
            List<String> images = Arrays.stream(status.getMediaEntities())
                    .filter(mediaEntity -> "photo".equals(mediaEntity.getType()))
                    .map(MediaEntity::getMediaURL)
                    .collect(Collectors.toList());
            if (images.isEmpty()) {
                LOGGER.info(format("Contains images %s %n", images));
            }
            if (!images.isEmpty()) {
                if (imageIdentifier.detectLandmarks(images)) {
                    reply(status, statuses.get(status.getLang()).getNextText());
                }
            }
        } catch(Exception e) {
            LOGGER.warn("Error receiving status", e);
        }
    }
}
