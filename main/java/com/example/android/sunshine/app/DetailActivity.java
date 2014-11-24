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
