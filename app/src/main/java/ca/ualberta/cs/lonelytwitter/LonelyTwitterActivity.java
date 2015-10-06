package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav"; // model
	private EditText bodyText; // view
	private ListView oldTweetsList; // view
	private ArrayList<Tweet> tweets; // model
	private ArrayAdapter<Tweet> adapter; // controller


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // view

		bodyText = (EditText) findViewById(R.id.body); // view
		Button saveButton = (Button) findViewById(R.id.save); // controller (b/c onClick method)
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); // view

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// controller
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				// model
				tweets.add(new NormalTweet(text));
				saveInFile();
				// controller
				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadFromFile(); // model
		if (tweets == null) {
			throw new RuntimeException();
		}
		adapter = new ArrayAdapter<Tweet>(this, R.layout.list_item, tweets); // controller
		oldTweetsList.setAdapter(adapter); // controller
	}

	private void loadFromFile() {
		// model

		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			// Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html retrieved 2015-09-21
			Type listType = new TypeToken<ArrayList<NormalTweet>>() {}.getType();
			tweets = gson.fromJson(in, listType);

		} catch (FileNotFoundException e) {
			tweets = new ArrayList<Tweet>();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void saveInFile() {
		// model
		
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					0);
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			Gson gson = new Gson();
			gson.toJson(tweets, writer);
			writer.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}