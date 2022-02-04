package com.github.jayachandhar.service;

import com.github.jayachandhar.model.UserProfile;
import com.github.jayachandhar.utils.Util;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.TimeZone;


@Service
public class TweetService {
    private final Twitter authenticatedTwitter;
    private final SimpleDateFormat sdf;

    public TweetService() {
        authenticatedTwitter = TwitterFactory.getSingleton();
        sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
    }

    private void getTweetAnalysis(UserProfile userProfile) throws TwitterException {
        List<Status> statuses;
        sdf.applyPattern("HH");
        Paging paging = new Paging(1, 100);
        statuses = authenticatedTwitter.getUserTimeline(userProfile.getScreenName(), paging);
        StringBuilder content = new StringBuilder();
        Map<Integer, Integer> tweetTiming = new TreeMap<>();
        List<HashtagEntity> hashtags = new ArrayList<>();
        List<UserMentionEntity> userMentionEntities = new ArrayList<>();
        if (statuses.size() == 0) {
            return;
        }
        for (Status status : statuses) {
            content.append(status.getText().concat(" "));
            hashtags.addAll(Arrays.asList(status.getHashtagEntities()));
            userMentionEntities.addAll(Arrays.asList(status.getUserMentionEntities()));
            Integer hour = Integer.valueOf(sdf.format(status.getCreatedAt()));
            tweetTiming.put(hour, tweetTiming.containsKey(hour) ? tweetTiming.get(hour) + 1 : 1);
        }
        for (UserMentionEntity userMentionEntity : userMentionEntities) {
            String screenName = userMentionEntity.getScreenName();
            userProfile.getMentionsByCount().put(screenName, userProfile.getMentionsByCount().containsKey(screenName) ?
                    userProfile.getMentionsByCount().get(screenName) + 1 : 1);
        }
        for (HashtagEntity hashtagEntity : hashtags) {
            String hashtag = hashtagEntity.getText();
            userProfile.getHashtagBycount().put(hashtag, userProfile.getHashtagBycount().containsKey(hashtag) ?
                    userProfile.getHashtagBycount().get(hashtag) + 1 : 1);
        }
        userProfile.setTweetTiming(tweetTiming);
        Map<String, Integer> wordsCounts = new HashMap<>();
        for (String word : content.toString().split(" ")) {
            if (StringUtils.isAlpha(word) && StringUtils.isAsciiPrintable(word))
                if (!Util.WORDS_TO_AVOID.contains(word.toLowerCase()))
                    wordsCounts.put(word.toLowerCase(), wordsCounts.containsKey(word.toLowerCase()) ?
                            wordsCounts.get(word.toLowerCase()) + 1 : 1);
        }
        userProfile.setRetweetCount(wordsCounts.containsKey("rt") ? wordsCounts.remove("rt") : 0);
        userProfile.setOriginalTweetCount(userProfile.getRetweetCount() == 0 ?
                userProfile.getTweetCount() : statuses.size() - userProfile.getRetweetCount());

        userProfile.setWordByFrequency(Util.sortMap(wordsCounts));
        userProfile.setMentionsByCount(Util.sortMap(userProfile.getMentionsByCount()));
        userProfile.setHashtagBycount(Util.sortMap(userProfile.getHashtagBycount()));
        if (statuses.size() > 5) {
            String tweetFrequency = calcStatusFrequency(statuses.get(0).getCreatedAt().getTime(),
                    statuses.get(statuses.size() - 1).getCreatedAt().getTime());
            userProfile.setStatusFrequency(tweetFrequency.split(":")[0]);
            userProfile.setTweetPerDay(tweetFrequency.split(":")[1]);
        }
    }

    private void profileAnalysis(UserProfile userProfile) throws TwitterException {
        sdf.applyPattern("dd/MM/yy hh:mm a z");
        User user = authenticatedTwitter.showUser(userProfile.getScreenName());
        userProfile.setName(StringUtils.isBlank(user.getName()) ? "Not Available" : user.getName());
        userProfile.setJoinedOn(StringUtils.isBlank(user.getCreatedAt().toString()) ?
                "Not Available" : sdf.format(user.getCreatedAt()));
        userProfile.setLocation(StringUtils.isBlank(user.getLocation()) ? "Not Available" : user.getLocation());
        userProfile.setBio(StringUtils.isBlank(user.getDescription()) ? "Not Available" : user.getDescription());
        userProfile.setTweetCount(user.getStatusesCount());
        userProfile.setFollowingCount(user.getFriendsCount());
        userProfile.setFollowerCount(user.getFollowersCount());
        userProfile.setImageURL(user.getProfileImageURLHttps());
        userProfile.setRatio((float) userProfile.getFollowerCount() / (userProfile.getFollowingCount() == 0 ?
                1 : userProfile.getFollowingCount()));
    }

    private String calcStatusFrequency(long recent, long start) {
        long days = (recent - start) / (1000 * 60 * 60 * 24);
        float avgTweetCount = (float) 100 / days;
        String frequency;
        if (avgTweetCount > 5) //more than 5 tweets per day on average-Frequent
            frequency = "Frequent";
        else {
            if (avgTweetCount < 1) //less than 1 tweets per day on average-Rare
                frequency = "Rare";
            else
                frequency = "Moderate"; //between 1-5 tweets per day on average-Moderate
        }
        return frequency + ":" + avgTweetCount;
    }

    public UserProfile getAnalysis(String screenName) throws TwitterException {
        UserProfile userProfile = new UserProfile();
        userProfile.setScreenName(screenName);
        profileAnalysis(userProfile);
        getTweetAnalysis(userProfile);
        return userProfile;
    }

}
