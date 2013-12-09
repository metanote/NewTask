package com.example.newtask;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.EditText;

public class ContextManagementActivity extends Activity {
	private static final String CONTEXT_SERVER_URL = "http://10.0.2.2:8083/";
	String room = "room01";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new SimpleHttpGetTask(this).execute(CONTEXT_SERVER_URL + room + "/light/on");

		((EditText) findViewById(R.id.editText1))
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH
								|| actionId == EditorInfo.IME_ACTION_DONE
								|| event.getAction() == KeyEvent.ACTION_DOWN
								&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
							if (!event.isShiftPressed()) {
								// the user is done typing.
								room = ((EditText) findViewById(R.id.editText1))
										.getText().toString();
								retrieveRoomContextState(room);
								return true; // consume.
							}
						}
						return false; // pass on to other listeners.
					}

				});

	}

	private void retrieveRoomContextState(String room) {
		// TODO Auto-generated method stub

	}

	// private void extractStatus(String serverResponse) throws IOException,
	// FileNotFoundException {
	//
	// if (serverResponse != null) {
	// if (serverResponse
	// .startsWith("File Not Found")) {
	// // do something to inform the file is not found
	// } else if (serverResponse.startsWith("on")
	// {
	// status = "on";
	// } else if (serverResponse.startsWith("off") {
	// status = "off";
	// } else
	// // do something to inform the response is malformed
	// } else
	// // do something to inform the server is not found
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.context_management, menu);
		return true;
	}

	public void setResponse(String response) {
		// TODO Auto-generated method stub
		Log.d("test",response);
	}

}
