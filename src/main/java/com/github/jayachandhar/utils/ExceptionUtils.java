package com.github.jayachandhar.utils;

import twitter4j.TwitterException;

public class ExceptionUtils {
    public static String getExceptionReason(Exception e) {
        if (e.getClass() == TwitterException.class) {
            String reason = "";
            if (((TwitterException) e).getStatusCode() == 401) {
                reason = "your account's private policy. Make sure that your account is open for public viewing " +
                        "and then retry.";
            }
            if (((TwitterException) e).getStatusCode() == 404) {
                reason = "No matching Account found for this screen name. please check your Twitter Screen Name" +
                        " and retry.";
            }
            if (((TwitterException) e).getStatusCode() == 403) {
                reason = "This screen name has been modified recently. please try with the new screen name.";
            }
            return reason;
        } else
            return null;


    }
}
