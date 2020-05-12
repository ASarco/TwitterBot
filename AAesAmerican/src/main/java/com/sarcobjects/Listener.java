package com.sarcobjects;

import twitter4j.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

class Listener implements StatusListener {

    private static final Logger LOGGER = Logger.getLogger(Listener.class);

    static final String MY_USER_NAME = "aesamerican";
    static final String CATEGORY = "American";

    private final Twitter twitter;
    private final CircularBuffer statuses;
    private final Categoriser categoriser;

    public Listener(Twitter twitter) throws IOException {
        this.twitter = twitter;
        this.statuses = new CircularBuffer();
        this.categoriser = new Categoriser("/es-doccat.bin");
    }

    @Override
    public void onStatus(Status status) {

        if (!MY_USER_NAME.equalsIgnoreCase(status.getUser().getScreenName()) && !status.isRetweet()) {
            String text = status.getText();
            if (status.getQuotedStatus() != null) {
                text += "|" + status.getQuotedStatus().getText();
            }
            String country = "Unknown";
            if (nonNull(status.getPlace())) {
                country = status.getPlace().getCountry();
            }
            LOGGER.info(format("%n%nFrom: %s Country: %s Text: %s", status.getUser().getScreenName(),
                    country, "Unknown"), text);
            //if (CATEGORY.equalsIgnoreCase(categoriser.categorise(text))) {

                LOGGER.info("REPLYING =====>");
                reply(status);
            //}
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {

    }

    void reply(Status status) {

        StringBuilder update = new StringBuilder();
        update.append(statuses.getNextText());
        update.append(" @").append(status.getUser().getScreenName());

        UserMentionEntity[] userMentionEntities = status.getUserMentionEntities();
        String mentions = Arrays.stream(userMentionEntities)
                .map(UserMentionEntity::getScreenName)
                .map(name -> "@" + name)
                .collect(Collectors.joining(" ", " ", ""));
        update.append(mentions);

        StatusUpdate statusUpdate = new StatusUpdate(update.toString());
        statusUpdate.autoPopulateReplyMetadata(true);
        statusUpdate.setInReplyToStatusId(status.getId());
        try {
            twitter.updateStatus(statusUpdate);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

}
