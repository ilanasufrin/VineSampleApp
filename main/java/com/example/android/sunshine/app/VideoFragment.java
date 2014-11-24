1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
83
84
85
86
87
88
89
90
91
92
93
94
95
96
97
98
99
100
101
102
103
104
105
106
107
108
109
110
111
112
113
114
115
116
117
118
119
120
121
122
123
124
125
126
127
128
129
130
131
132
133
134
135
136
137
138
139
140
141
142
143
144
145
146
147
148
149
150
151
152
153
154
155
156
157
158
159
160
161
162
163
164
165
166
167
168
169
170
171
172
173
174
175
176
177
178
179
180
181
182
183
184
185
186
187
188
189
190
191
192
193
194
195
196
197
198
199
200
201
202
203
204
205
206
207
208
209
210
211
212
213
214
215
216
217
218
219
220
221
222
223
224
225
226
227
228
229
230
231
232
233
234
235
236
237
238
239
240
241
242
243
244
245
246
247
248
249
250
251
252
253
254
255
256
257
258
259
260
261
262
263
264
265
266
267
268
269
270
271
272
273
274
275
276
277
278
279
280
281
282
283
284
285
286
287
288
289
290
291
292
293
294
295
296
297
298
299
300
301
302
303
304
305
306
307
308
309
310
311
312
313
314
315
316
317
318
319
320
321
322
323
324
325
326
327
328
329
330
331
332
333
334
335
336
337
338
339
340
341
342
343
344
345
346
347
348
349
350
351
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
 
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
import com.squareup.picasso.Picasso;
 
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
 
    private VideoAdapter videoAdapter;
 
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
 
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //once again, if I decide to add options, this is where it'll go
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
        videoAdapter = new VideoAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_video);
 
//        videoAdapter.add("http://i.imgur.com/DvpvklR.png");
 
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.v(LOG_TAG, "view has been inflated");
 
        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_video);
        listView.setAdapter(videoAdapter);
 
 
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
            //this is where we're creating the detailed list item views
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
 
 
                String forecast = videoAdapter.getItem(position).getmVideoUrl();
 
 
               String toastMessage = new String("taking you to the video");
                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
 
                Intent myIntent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(myIntent);
 
            }
 
        });
 
        updateVideos();
 
        return rootView;
    }
 
    public class FetchVideoTask extends AsyncTask<String, Void, ArrayList<VideoObject>> {
 
        private final String LOG_TAG = FetchVideoTask.class.getSimpleName();
 
        @Override
        protected ArrayList<VideoObject> doInBackground(String... params) {
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
        private ArrayList<VideoObject>  getVideoDataFromJson(String forecastJsonStr)
                throws JSONException {
 
            // These are the names of the JSON objects that need to be extracted.
            final String OWM_DATA = "data";
 
 
            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONObject weatherArray = forecastJson.getJSONObject(OWM_DATA);
 
            //we know can pass in string. but what about objects?
            ArrayList<VideoObject> resultStrs = new ArrayList<VideoObject>();
            for(int i = 0; i < weatherArray.length(); i++) {
 
                String thumbnail;
                JSONObject likesObject;
                JSONArray records;
 
 
                // Get the JSON object representing all the records
                JSONArray dayForecast = weatherArray.getJSONArray("records");
 
 
                //now we iterate over that string, looking for 'likes' objects
                for (int j = 0; j < dayForecast.length(); j ++) {
                    likesObject = dayForecast.getJSONObject(j);
 
//
 
                    //there only seem to be about 10 unique results
//                    resultStrs.add(likesObject.getString("thumbnailUrl") + " posted by " + likesObject.getString("username"));
 
                    VideoObject myVideoObject = new VideoObject(likesObject.getString("thumbnailUrl"), likesObject.getString("username"), likesObject.getString("videoLowURL"));
                    resultStrs.add(myVideoObject);
 
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
        protected void onPostExecute(ArrayList<VideoObject> result) {
            if (result != null) {
                videoAdapter.clear();
                for (VideoObject dayForecastString : result) {
 
                    videoAdapter.add(dayForecastString);
                }
                videoAdapter.notifyDataSetChanged();
            }
        }
 
 
    }
 
    private class VideoAdapter extends ArrayAdapter<VideoObject> {
 
        //can later change to type object
 
        public VideoAdapter(Context context, int resource ) {
            super(context, resource);
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            VideoObject videothumbnailstring = getItem(position);
            View rowView = inflater.inflate(R.layout.list_item_video, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.list_item_video_textview);
            textView.setText("Posted by " + videothumbnailstring.getmName());
 
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
 
 
            Picasso.with(getActivity()).load(videothumbnailstring.getmThumbnailUrl()).resize(200, 200).centerCrop().into(imageView);
           //used for testing that the library works
//        Picasso.with(imageView.getContext()).load("http://i.imgur.com/DvpvklR.png").into(imageView);
 
            return rowView;
        }
    }
 
 
 
}