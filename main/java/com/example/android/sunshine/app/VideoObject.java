package com.example.android.sunshine.app;

/**
 * Created by ilana.sufrin on 11/19/14.
 */
public class VideoObject { // implements Parcelable {
    private String mThumbnailUrl;
    private String mName;
    private String mVideoUrl;


    public VideoObject(String mThumbnailUrl, String mName, String mVideoUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
        this.mName = mName;
        this.mVideoUrl = mVideoUrl;
    }


    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }



    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setmThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

   // http://developer.android.com/reference/android/os/Parcelable.html


}
