package ca.ualberta.cs.lonelytwitter;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by ross on 15-10-05.
 */
public interface MyObserver {
    void notify(MyObservable obj);
}
