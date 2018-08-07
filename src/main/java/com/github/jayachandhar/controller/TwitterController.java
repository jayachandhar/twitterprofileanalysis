package com.github.jayachandhar.controller;

import com.github.jayachandhar.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

@RestController
@RequestMapping("/api/analyze")
public class TwitterController {
    @Autowired
    private TweetService tweetService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/{screenName}", method = RequestMethod.GET)
    public Object getService(@PathVariable String screenName) {
        try {
            logger.debug("Request received for Screen Name : " + screenName);
            Object profile = tweetService.getAnalysis(screenName);
            logger.debug("Request processed for Screen Name : " + screenName);
            return profile;
        } catch (Exception e) {
            logger.error("unable to process for ScreenName : " + screenName + " Reason:" + e.getMessage());
            if (e.getClass() == TwitterException.class)
                return new ResponseEntity("Content not available due to " +
                        ((TwitterException) e).getErrorMessage(), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity("Content not available try after some time " +
                        ((TwitterException) e).getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
