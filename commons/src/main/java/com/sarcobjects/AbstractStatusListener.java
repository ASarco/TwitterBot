package com.sarcobjects;

import twitter4j.*;

public abstract class AbstractStatusListener implements StatusListener {

    private static final Logger LOGGER = Logger.getLogger(AbstractStatusListener.class);

    final Twitter twitter;


    protected AbstractStatusListener(Twitter twitter) {
        this.twitter = twitter;
    }

    void reply(Status status, String reply) {
        LOGGER.info("Replying ====> " + reply);

        StatusUpdate statusUpdate = new StatusUpdate(reply + " @" + status.getUser().getScreenName());
        statusUpdate.setInReplyToStatusId(status.getId());
        statusUpdate.autoPopulateReplyMetadata(true);
        try {
            twitter.updateStatus(statusUpdate);
        } catch (TwitterException e) {
            LOGGER.warn("Twitter exception when trying to reply", e);
        }
    }


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
