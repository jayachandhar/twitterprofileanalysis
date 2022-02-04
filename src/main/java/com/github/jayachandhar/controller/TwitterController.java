package com.github.jayachandhar.controller;

import com.github.jayachandhar.model.UserProfile;
import com.github.jayachandhar.service.TweetService;
import com.github.jayachandhar.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TwitterController {
    @Autowired
    private TweetService tweetService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/analyze/{screenName}")
    public ResponseEntity getService(@PathVariable final String screenName) {
        logger.debug("Request received for Screen Name : " + screenName);
        UserProfile profile = null;
        try {
            profile = tweetService.getAnalysis(screenName);
        } catch (Exception e) {
            String reason = ExceptionUtils.getExceptionReason(e);
            logger.error("Request failed for ScreenName : " + screenName + " => " + reason);
            return new ResponseEntity("Content not available try after some time." + ("".equals(reason) ?
                    "" : "\nReason : " + reason), HttpStatus.BAD_REQUEST);
        }
        logger.debug("Request processed for Screen Name : " + screenName);
        return new ResponseEntity(profile, HttpStatus.OK);
    }
}
