//package com.example.android.sunshine.app;
//
//import android.content.Intent;
//import android.media.MediaPlayer;
//
///**
// * Created by ilana.sufrin on 11/20/14.
// */
//public class VideoPlayer extends Service implements MediaPlayer.OnPreparedListener {
//    private static final String ACTION_PLAY = "com.example.action.PLAY";
//    MediaPlayer mMediaPlayer = null;
//
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        ...
//        if (intent.getAction().equals(ACTION_PLAY)) {
//            mMediaPlayer = ... // initialize it here
//            mMediaPlayer.setOnPreparedListener(this);
//            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
//        }
//    }
//
//    /** Called when MediaPlayer is ready */
//    public void onPrepared(MediaPlayer player) {
//        player.start();
//    }
//}