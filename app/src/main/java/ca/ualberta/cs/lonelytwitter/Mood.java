package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by rhanders on 9/14/15.
 */
public abstract class Mood {

    private Date date;

    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public abstract String format();
}
