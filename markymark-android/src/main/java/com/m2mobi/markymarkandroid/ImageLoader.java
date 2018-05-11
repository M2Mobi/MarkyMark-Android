package com.m2mobi.markymarkandroid;

import android.support.annotation.NonNull;
import android.widget.ImageView;

public interface ImageLoader {

    /**
     * Called when an image should be loaded
     *
     * @param targetImageView The ImageView in which the image should be loaded
     * @param url             The URL of the image that should be loaded
     */
    void loadImage(@NonNull ImageView targetImageView, @NonNull String url);
}
