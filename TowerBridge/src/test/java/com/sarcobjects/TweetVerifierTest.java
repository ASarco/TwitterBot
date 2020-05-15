package com.sarcobjects;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TweetVerifierTest {

    @Test
    public void verify() {

        assertThat(TweetVerifier.shouldReply("London bridge que linda foto")).isTrue();
        assertThat(TweetVerifier.shouldReply("Puente de Londres que linda foto")).isTrue();
        assertThat(TweetVerifier.shouldReply("Puente Londres que linda foto")).isTrue();
        assertThat(TweetVerifier.shouldReply("PuenteLondres que linda foto")).isTrue();
        assertThat(TweetVerifier.shouldReply("London bridge no es tower bridge")).isFalse();
        assertThat(TweetVerifier.shouldReply("Esto no tiene nada que ver")).isFalse();
        assertThat(TweetVerifier.shouldReply("El puente de la torre en Londres")).isFalse();
        assertThat(TweetVerifier.shouldReply("Foto de la estación London Bridge")).isFalse();
        assertThat(TweetVerifier.shouldReply("FOTO de la ESTACIÓN LONDON BRIDGE")).isFalse();
    }
}