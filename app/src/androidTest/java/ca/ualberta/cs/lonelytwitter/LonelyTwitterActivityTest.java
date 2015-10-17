package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2 {

    private EditText bodyText;
    private Button saveButton;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testEditTweet() {
        // when you call getactivity android will start the app and the activity
        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();

        // reset the app to a known state
        activity.getTweets().clear();

        // add a tweet using the UI
        bodyText = activity.getBodyText();
        final String tweetText = "test tweet";
        activity.runOnUiThread(new Runnable() {
            public void run() {
                bodyText.setText(tweetText);
            }
        });
        getInstrumentation().waitForIdleSync();

        saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // make sure the tweet was actually added
        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals(tweetText, tweet.getText());


        // ensure the tweet editor activity starts up
        // following from https://developer.android.com/training/activity-testing/activity-functional-testing.html 2015-10-13
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditTweetActivity.class.getName(),
                        null, false);
        // the code where they clicked was here
        // click on the tweet to edit
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetsList.getChildAt(0);
                oldTweetsList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();
        // Validate that ReceiverActivity is started
        final EditTweetActivity receiverActivity = (EditTweetActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditTweetActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // test that the editor starts up with the right tweet in it to edit
        assertEquals(tweetText, receiverActivity.getTweetText());

        // test that we can edit that tweet
        final String editedTweetText = "edited tweet";
        final EditTweetActivity act = receiverActivity;
        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                act.editTweet(editedTweetText);
                assertEquals(editedTweetText, act.getTweetText());
            }
        });

        // test that we can push some kind of save button for that tweet
        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                receiverActivity.getSaveButton().performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // the new modified tweet text was actually saved
        assertEquals(editedTweetText, ApplicationState.getInstance().getEditingTweet().getText());

        // the new modified tweet text is displayed on the other activity
        activity.runOnUiThread(new Runnable() {
            public void run() {
                TextView tv = (TextView) oldTweetsList.getChildAt(0);
                assertEquals(editedTweetText, tv.getText().toString());
            }
        });
        getInstrumentation().waitForIdleSync();

        // clean up our activities at the end of our test
        receiverActivity.finish();
    }
}