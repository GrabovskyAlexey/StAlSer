package ru.stalser.framework.helpers;


import org.assertj.core.api.SoftAssertions;

public class SoftAssertHelper {

    protected static final ThreadLocal<SoftAssertions> softAssertions = new ThreadLocal<>();

    public static SoftAssertions softly() {
        return softAssertions.get();
    }

    public static void reset() {
        softAssertions.set(new SoftAssertions());
    }

    public static void failTestIfLastAssertWasFailed() {

        if (!softly().wasSuccess()) {
            softly().assertAll();
        }
    }
}
