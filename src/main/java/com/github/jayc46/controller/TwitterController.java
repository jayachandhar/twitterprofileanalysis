package com.github.jayc46.controller;

import com.github.jayc46.model.UserProfile;
import com.github.jayc46.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;

@RestController
@RequestMapping("/api/analyze")
public class TwitterController {
    @Autowired
    private TweetService tweetService;

    @RequestMapping( value = "/{screenName}",method = RequestMethod.GET)
    public Object getService(@PathVariable String screenName) {
        try {
            return tweetService.getAnalysis(screenName);
        } catch (TwitterException e) {
            return "Content not available";
        }
    }
}
