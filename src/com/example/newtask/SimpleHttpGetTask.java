package com.example.newtask;

import android.os.AsyncTask;
import android.widget.Toast;
import com.example.newtask.*;

public class SimpleHttpGetTask extends AsyncTask<String, String, String> {

    protected ContextManagementActivity activity;

    public SimpleHttpGetTask(ContextManagementActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String path = params[0];
        return HttpUtils.httpGet(path);
    }

    protected void onPostExecute(String response) {
        // do something with the response...
        activity.setResponse(response);
    }
}