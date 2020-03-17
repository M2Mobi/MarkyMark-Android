package com.m2mobi.markymark

import android.content.Context
import android.widget.ImageView
import com.m2mobi.markymarkandroid.ImageLoader
import com.squareup.picasso.Picasso

class PicassoImageLoader(private val context: Context) : ImageLoader {

    override fun loadImage(targetImageView: ImageView, url: String) {
        Picasso.with(context)
                .load(url)
                .into(targetImageView)
    }
}