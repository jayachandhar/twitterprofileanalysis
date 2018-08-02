package com.github.jayachandhar.controller;

import com.github.jayachandhar.service.TweetService;
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

    @RequestMapping(value = "/{screenName}", method = RequestMethod.GET)
    public Object getService(@PathVariable String screenName) {
        try {
            return tweetService.getAnalysis(screenName);
        } catch (TwitterException e) {
            return new ResponseEntity("Content not available due to " + e.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
