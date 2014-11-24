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
package com.example.android.sunshine.app;
 
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;
 
 
public class DetailActivity extends ActionBarActivity {
    private SurfaceHolder holder;
    private SurfaceView mPreview;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
 
 
    }
 
 
        //if I decide to add an options menu later this is where it'll go
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        return true;
//    }
 
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
 
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        public SurfaceHolder holder;
      //  private SurfaceView mPreview;
 
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
 
        public DetailFragment() {
        }
 
 
 
 
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.video_detail, container, false);
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
               VideoView mVideoView = ((VideoView) rootView.findViewById(R.id.detail_text));
 
                mVideoView.setVideoPath(forecastStr);
 
 
 
                String url = forecastStr; // your URL here
                Log.v(LOG_TAG, "url for video:" + url);
//                MediaPlayer mediaPlayer = new MediaPlayer();
//
//
////                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                try {
//                    mediaPlayer.setDataSource(url);
//
//                } catch (IOException e) {
//                    Log.e(LOG_TAG, "cannot find url for videos");
//                }
//                try {
//                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                } catch (IOException e) {
//                    Log.e(LOG_TAG, "taking too long to play videos");
//                }
//                Log.v(LOG_TAG, "about to start media player");
                //turns out I don't need a MediaPlayer because VideoView is a wrapper for it
 
                mVideoView.requestFocus();
                mVideoView.start();
 
            }
                return rootView;
            }
 
 
    }
 
 
 
 
}