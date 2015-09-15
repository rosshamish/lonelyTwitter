package ca.ualberta.cs.lonelytwitter;

import java.io.IOException;
import java.util.Date;

/**
 * Created by rhanders on 9/14/15.
 */
public abstract class Tweet implements Tweetable {
    private String text;
    private Date date;

    public Tweet(String text, Date date) throws IOException {
        this.setText(text);
        this.date = date;
    }

    public Tweet(String text) throws IOException {
        this.setText(text);
        this.date = new Date();
    }

    public abstract Boolean isImportant();

    public String getText() {
        return text;
    }

    public void setText(String text) throws IOException {
        if (text.length() > 140) {
            throw new IOException("Tweet is too long!");
        }
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
