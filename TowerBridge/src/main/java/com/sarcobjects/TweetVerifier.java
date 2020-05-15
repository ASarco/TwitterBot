package com.sarcobjects;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class TweetVerifier {

    //List of regex to verify tweets
    private static final List<Pattern> tracked = List.of(
            compile("london\\s*bridge|puente\\s*(?:de)*\\s*londres"),
            compile("^((?!tower).)*$"),
            compile("^((?!torre).)*$"),
            compile("^((?!station).)*$"),
            compile("^((?!estaci[oó]n).)*$"));


    public static boolean shouldReply(String tweet) {
        return tracked.stream()
                .allMatch(pattern -> pattern.matcher(tweet.toLowerCase()).find());
    }
}
