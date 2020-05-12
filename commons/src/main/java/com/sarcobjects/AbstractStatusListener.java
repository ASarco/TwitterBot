package com.sarcobjects;

import twitter4j.Logger;
import twitter4j.StallWarning;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public abstract class AbstractStatusListener implements StatusListener {

    private static final Logger LOGGER = Logger.getLogger(AbstractStatusListener.class);


    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {
        LOGGER.warn("Track Limitation happened: " + i);
    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {
        LOGGER.warn("Stall happened : ", stallWarning.getMessage());

    }

    @Override
    public void onException(Exception e) {
        LOGGER.warn("Exception happened", e);
    }
}
