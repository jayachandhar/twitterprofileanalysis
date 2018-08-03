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
    private Twitter authenticatedTwitter;
    private SimpleDateFormat sdf;

    public TweetService() {
        authenticatedTwitter = TwitterFactory.getSingleton();
        sdf = new SimpleDateFormat("HH");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
    }

    private void getTweetAnalysis(UserProfile userProfile) throws TwitterException {
        List<Status> statuses;
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
            //uncomment to avoid replies
            // content.append(status.getInReplyToScreenName() != null ? "" : status.getText() + " ");

            //uncomment to avoid retweets
            // content.append(status.getText().contains("RT") ? "" : status.getText() + " ");

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
        // List<Map.Entry<String, Integer>> list = new LinkedList<>();
        for (String word : content.toString().split(" ")) {
            if (StringUtils.isAlpha(word) && StringUtils.isAsciiPrintable(word))
                if (!Util.WORDS_TO_AVOID.contains(word.toLowerCase()))
                    wordsCounts.put(word.toLowerCase(), wordsCounts.containsKey(word.toLowerCase()) ?
                            wordsCounts.get(word.toLowerCase()) + 1 : 1);
        }
        Map<String, Integer> wordsCountsDup = new HashMap<>(wordsCounts);
        for (Map.Entry<String, Integer> wordcount : wordsCountsDup.entrySet()) {
            if (wordcount.getValue() < 3)
                wordsCounts.remove(wordcount.getKey());
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
        User user = authenticatedTwitter.showUser(userProfile.getScreenName());
        userProfile.setName(StringUtils.isBlank(user.getName()) ? "Not Available" : user.getName());
        userProfile.setJoinedOn(StringUtils.isBlank(user.getCreatedAt().toString()) ? "Not Available" : user.getCreatedAt().toString());
        userProfile.setLocation(StringUtils.isBlank(user.getLocation()) ? "Not Available" : user.getLocation());
        Locale loc = LocaleUtils.toLocale(user.getLang().contains("-") ? user.getLang().split("-")[0] + "_" + user.getLang().split("-")[1].toUpperCase() : user.getLang());
        String language = loc.getDisplayLanguage();
        userProfile.setLanguage(language);
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
