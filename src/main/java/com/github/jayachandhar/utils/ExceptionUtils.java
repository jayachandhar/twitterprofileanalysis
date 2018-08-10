package com.github.jayachandhar.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import twitter4j.TwitterException;

public class ExceptionUtils {
    public static ResponseEntity getExceptionReason(Exception e) {
        if (e.getClass() == TwitterException.class) {
            String reason = "";
            if (((TwitterException) e).getStatusCode() == 401) {
                reason = "your account's private policy. Make sure that your account is open for public viewing and then retry.";
            }
            if (((TwitterException) e).getStatusCode() == 404) {
                reason = "No matching Account found for this screen name. please check your Twitter Screen Name and retry";
            }
            return new ResponseEntity("Content not available.\nReason : " + reason, HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity("Content not available try after some time.", HttpStatus.BAD_REQUEST);


    }
}
