package com.github.jayc46.model;

import java.util.Objects;

public class MentionedProfile {
    private String screenName;
    private int count = 1;
    private String tinyImageURL;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increaseCount() {
        this.count++;
    }

    public String getTinyImageURL() {
        return tinyImageURL;
    }

    public void setTinyImageURL(String tinyImageURL) {
        this.tinyImageURL = tinyImageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MentionedProfile that = (MentionedProfile) o;
        return Objects.equals(screenName, that.screenName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(screenName);
    }
}
