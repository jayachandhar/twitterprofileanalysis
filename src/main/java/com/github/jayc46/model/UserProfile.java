package com.github.jayc46.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserProfile {
    private String screenName;
    private String name;
    private String imageURL;
    private String joinedOn;
    private String location;
    private String language;
    private String bio;
    private String profileURL;
    private String timeZone;
    private int followerCount;
    private int followingCount;
    private int tweetCount;
    private int originalTweetCount;
    private int retweetCount;
    private float ratio;
    private String statusFrequency;
    private String tweetPerDay;
    private Map<String, Integer> wordByFrequency = new LinkedHashMap<>();
    private Map<String, Integer> hashtagBycount = new LinkedHashMap<>();
    private Map<String, Integer> mentionsByCount = new LinkedHashMap<>();
    private Map<Integer, Integer> tweetTiming;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }

    public int getOriginalTweetCount() {
        return originalTweetCount;
    }

    public void setOriginalTweetCount(int originalTweetCount) {
        this.originalTweetCount = originalTweetCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetcount) {
        this.retweetCount = retweetcount;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public String getStatusFrequency() {
        return statusFrequency;
    }

    public void setStatusFrequency(String statusFrequency) {
        this.statusFrequency = statusFrequency;
    }

    public String getTweetPerDay() {
        return tweetPerDay;
    }

    public void setTweetPerDay(String tweetPerDay) {
        this.tweetPerDay = tweetPerDay;
    }

    public Map<String, Integer> getWordByFrequency() {
        return wordByFrequency;
    }

    public void setWordByFrequency(Map<String, Integer> wordByFrequency) {
        this.wordByFrequency = wordByFrequency;
    }

    public Map<String, Integer> getHashtagBycount() {
        return hashtagBycount;
    }

    public void setHashtagBycount(Map<String, Integer> hashtagBycount) {
        this.hashtagBycount = hashtagBycount;
    }

    public Map<String, Integer> getMentionsByCount() {
        return mentionsByCount;
    }

    public void setMentionsByCount(Map<String, Integer> mentionsByCount) {
        this.mentionsByCount = mentionsByCount;
    }

    public Map<Integer, Integer> getTweetTiming() {
        return tweetTiming;
    }

    public void setTweetTiming(Map<Integer, Integer> tweetTiming) {
        this.tweetTiming = tweetTiming;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "screenName='" + screenName + '\'' +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", joinedOn='" + joinedOn + '\'' +
                ", location='" + location + '\'' +
                ", language='" + language + '\'' +
                ", bio='" + bio + '\'' +
                ", profileURL='" + profileURL + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", followerCount=" + followerCount +
                ", followingCount=" + followingCount +
                ", tweetCount=" + tweetCount +
                ", originalTweetCount=" + originalTweetCount +
                ", retweetCount=" + retweetCount +
                ", ratio=" + ratio +
                ", statusFrequency='" + statusFrequency + '\'' +
                ", tweetPerDay='" + tweetPerDay + '\'' +
                ", wordByFrequency=" + wordByFrequency +
                ", hashtagBycount=" + hashtagBycount +
                ", mentionsByCount=" + mentionsByCount +
                ", tweetTiming=" + tweetTiming +
                '}';
    }
}