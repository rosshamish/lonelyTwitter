package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by joshua2 on 9/28/15.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 implements MyObserver {
    private Boolean wasNotified = Boolean.FALSE;

    public TweetListTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        wasNotified = Boolean.FALSE;
    }

    public void testAddObserver() {
        TweetList list = new TweetList();
        list.addObserver(this);
        list.add(new NormalTweet("test"));
        assertTrue(wasNotified);
    }

    public void testTweetObserver() {
        TweetList list = new TweetList();
        list.addObserver(this);
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        tweet.setText("different");
        assertTrue(wasNotified);
    }

    public void testAddTweet() {
        TweetList list = new TweetList();
        list.add(new NormalTweet("test"));
    }

    public void testDelete() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        list.delete(tweet);
        assertFalse(list.contains(tweet));
    }

    public void testContains() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        assertTrue(list.contains(tweet));
    }


    public void notify(MyObservable obj) {
        wasNotified = true;
    }
}