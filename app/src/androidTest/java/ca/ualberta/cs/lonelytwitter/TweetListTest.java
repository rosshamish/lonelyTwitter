package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ross on 15-09-28.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 {

    public TweetListTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testAdd() {
        TweetList list = new TweetList();
        list.addTweet(new NormalTweet("test"));
    }

    public void testAddSameTweetException() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        Boolean threwException = Boolean.FALSE;
        try {
            list.addTweet(tweet);
        } catch (IllegalArgumentException e) {
            threwException = Boolean.TRUE;
        }
        assertTrue(threwException);
    }

    public void testGetTweets() {
        TweetList list = new TweetList();
        list.addTweet(new NormalTweet("test"));
        assertTrue(list.getTweets() != null);
    }

    public void testGetTweetsInOrder() {
        TweetList list = new TweetList();
        Date d1 = new Date();
        Date d2 = new Date(d1.getTime() + 1000L);
        Tweet t1 = new NormalTweet("test", d1);
        Tweet t2 = new NormalTweet("testLater", d2);
        list.addTweet(t1);
        list.addTweet(t2);
        ArrayList<Tweet> tweets = list.getTweets();
        assertTrue(tweets.get(0).equals(t1));
        assertTrue(tweets.get(1).equals(t2));
        assertTrue(tweets.size() == 2);
    }

    public void testDelete() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        list.removeTweet(tweet);
        assertFalse(list.hasTweet(tweet));
    }

    public void testCount() {
        TweetList list = new TweetList();
        assertTrue(list.getCount() == 0);
        list.addTweet(new NormalTweet("test"));
        assertTrue(list.getCount() == 1);
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        assertTrue(list.getCount() == 2);
        list.removeTweet(tweet);
        assertTrue(list.getCount() == 1);
    }

}