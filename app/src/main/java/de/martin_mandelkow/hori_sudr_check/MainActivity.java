package de.martin_mandelkow.hori_sudr_check;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "de.martin_mandelkow.hori_sudr_check.MESSAGE";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL("http://www.horiversum.org/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                //InputStreamReader myinputstream = new InputStreamReader(urlConnection.getInputStream());

                Log.d("MyApp","URL is defined");
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                //BufferedReader reader = new BufferedReader(new InputStreamReader(myinputstream));
                String webPage = "", data = "";
                while ((data = reader.readLine()) != null) {
                    webPage += data + "\n";
                }
                urlConnection.disconnect();
                return webPage;
            } catch (MalformedURLException e) {
                System.out.println("The URL is not valid.");
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("IOException.");
                System.out.println(e.getMessage());
            }
        return "There was an error within doInBackground inside DownloadWebPageTask";
        }

        @Override
        protected void onPostExecute(String result) {
            sendMessageNext(result);
        }
    }
    private void sendMessageNext(String html_text) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = html_text;
        Log.d("MyApp", html_text);
        intent.putExtra(EXTRA_MESSAGE, html_text);
        startActivity(intent);
        Log.d("MyApp","It is all done.");
    }


    public void sendMessage(View view) {
        /**
         * Called when the user clicks the Send button
         */
        // Do something in response to button
        Log.d("MyApp", "sendMessage starts");
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://www.vogella.com/index.html"});
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://de.martin_mandelkow.hori_sudr_check/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://de.martin_mandelkow.hori_sudr_check/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

