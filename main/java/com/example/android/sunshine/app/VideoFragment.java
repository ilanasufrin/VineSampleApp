/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class VideoFragment extends Fragment {

    private ArrayAdapter<String> videoAdapter;

    public VideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.videofragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchVideoTask myFetchVideoTask = new FetchVideoTask();
            myFetchVideoTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateVideos() {
        FetchVideoTask myFetchVideoTask = new FetchVideoTask();
//       myFetchWeatherTask.execute("10003"); this hardcodes a string instead of getting it from preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //the second argument allows for a default location if the user hasn't set one
//        String location = prefs.getString(getString(R.string.pref_location_key),  getString(R.string.pref_location_default));
//        myFetchVideoTask.execute(location);
        myFetchVideoTask.execute();
    }

    //happens every time the fragment starts
    @Override
    public void onStart() {
        super.onStart();
        updateVideos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String LOG_TAG = FetchVideoTask.class.getSimpleName();

        // Create some dummy data for the ListView.  Here's a sample weekly forecast
        String[] data = {
                "Welcome! Loading your videos"
        };
        List<String> videoresponses = new ArrayList<String>(Arrays.asList(data));

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        videoAdapter = new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_video, // The name of the layout ID.
                        R.id.list_item_video_textview, // The ID of the textview to populate.
                        videoresponses);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.v(LOG_TAG, "view has been inflated");

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_video);
        listView.setAdapter(videoAdapter);



//        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        // Picasso.with(getActivity()).load(forecast).into(imageView);
//        Picasso.with(imageView.getContext()).load("http://i.imgur.com/DvpvklR.png").into(imageView);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //this is where we're creating the detailed list item views
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                String forecast = videoAdapter.getItem(position);
//
                String toastMessage = new String("taking you to the video");
                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(myIntent);

            }

        });

        return rootView;
    }

    public class FetchVideoTask extends AsyncTask<String, Void, ArrayList<String>> {

        private final String LOG_TAG = FetchVideoTask.class.getSimpleName();

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String videoJsonStr = null;



            try {
                // Construct the URL for the  query
                final String FORECAST_BASE_URL = "https://vine.co/api/timelines/users/918753190470619136";


                Uri builtURI = Uri.parse(FORECAST_BASE_URL).buildUpon()

                        .build();

                URL url = new URL(builtURI.toString());
                Log.v(LOG_TAG, "Built URI: " + builtURI.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                videoJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Video JSON string: " + videoJsonStr);
                try {
                    return getVideoDataFromJson(videoJsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        /**
         * Take the String representing the complete results in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private ArrayList<String>  getVideoDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_DATA = "data";


            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONObject weatherArray = forecastJson.getJSONObject(OWM_DATA);

            ArrayList<String> resultStrs = new ArrayList<String>();
            for(int i = 0; i < weatherArray.length(); i++) {

                String thumbnail;
                JSONObject likesObject;
                JSONArray records;


                // Get the JSON object representing all the records
                JSONArray dayForecast = weatherArray.getJSONArray("records");


                //now we iterate over that string, looking for 'likes' objects
                for (int j = 0; j < dayForecast.length(); j ++) {
                    likesObject = dayForecast.getJSONObject(j);
//                    resultStrs.add(likesObject.getString("thumbnailUrl"));
//


                    resultStrs.add(likesObject.getString("thumbnailUrl") + " posted by " + likesObject.getString("username"));

//                   for (int k = 0; k < likesObject.length(); k ++) {
////                       resultStrs.add(likesObject.getString("thumbnailUrl"));
////                       resultStrs.add(likesObject.getString("username"));
//                   }
                }

            }
            for (int i = 0; i <resultStrs.size(); i++) {

                    Log.v(LOG_TAG, "video array: " + resultStrs.get(i));

            }

            return resultStrs;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result != null) {
                videoAdapter.clear();
                for (String dayForecastString : result) {

                    videoAdapter.add(dayForecastString);
                }
            }
        }


    }



}