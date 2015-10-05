package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by joshua2 on 9/28/15.
 */
public class TweetList implements MyObservable, MyObserver {
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    private volatile ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    private void notifyAllObservers() {
        for (MyObserver observer : observers) {
            observer.notify(this);
        }
    }

    public void add(Tweet tweet) {
        tweets.add(tweet);
        notifyAllObservers();
    }

    public void delete(Tweet tweet) {
        tweets.remove(tweet);
        notifyAllObservers();
    }

    public Boolean contains(Tweet tweet) {
        return tweets.contains(tweet);
    }

    public void addObserver(MyObserver obs) {
        observers.add(obs);
    }

    public void notify(MyObservable obj) {
        notifyAllObservers();
    }
}
