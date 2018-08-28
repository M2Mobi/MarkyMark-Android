package com.m2mobi.markymarkandroid

import android.widget.ImageView

interface ImageLoader {

    /**
     * Called when an image should be loaded
     *
     * @param targetImageView The ImageView in which the image should be loaded
     * @param url             The URL of the image that should be loaded
     */
    fun loadImage(targetImageView: ImageView, url: String)
}
