package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTweetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);

        EditText etTweet = getEditText();
        Tweet tweet = ApplicationState.getInstance().getEditingTweet();
        etTweet.setText(tweet.getText());

        getSaveButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText et = getEditText();
                Tweet t = ApplicationState.getInstance().getEditingTweet();
                // t.setText(et.getText().toString()); // this is failing
                ApplicationState.getInstance().setEditingTweet(t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getTweetText() {
        EditText te = getEditText();
        return te.getText().toString();
    }

    public void editTweet(String text) {
        EditText te = getEditText();
        te.setText(text);
    }

    public EditText getEditText() {
        return (EditText) this.findViewById(R.id.etTweet);
    }

    public Button getSaveButton() {
        return (Button) this.findViewById(R.id.btnSaveTweet);
    }
}
