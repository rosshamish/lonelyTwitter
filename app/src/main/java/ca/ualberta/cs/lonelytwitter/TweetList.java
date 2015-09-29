package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ross on 15-09-28.
 */
public class TweetList {
    private ArrayList<Tweet> tweets;

    public TweetList() {
        tweets = new ArrayList<Tweet>();
    }

    public void addTweet(Tweet tweet) throws IllegalArgumentException {
        if (tweets.contains(tweet)) {
            throw new IllegalArgumentException("Tweet already exists");
        }
        tweets.add(tweet);
    }

    public ArrayList<Tweet> getTweets() {
        Collections.sort(tweets, new Comparator<Tweet>() {
            public int compare(Tweet lhs, Tweet rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });
        return tweets;
    }

    public Boolean hasTweet(Tweet tweet) {
        return tweets.contains(tweet);
    }

    public void removeTweet(Tweet tweet) {
        if (this.hasTweet(tweet)) {
            tweets.remove(tweet);
        }
    }

    public Integer getCount() {
        return tweets.size();
    }

}
