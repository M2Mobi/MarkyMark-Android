/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid

import android.content.Context
import android.text.Spanned
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams

import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymarkcommon.markdownitems.Image

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

/**
 * Converts an [Image] to a [View].
 */
class ImageDisplayItem(private val mContext: Context, private val mImageLoader: ImageLoader) : DisplayItem<View, Image, Spanned> {

    override fun create(pMarkdownItem: Image, pInlineConverter: InlineConverter<Spanned>): View {
        val imageView = ImageView(mContext, null, R.attr.MarkdownImageStyle)
        imageView.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        mImageLoader.loadImage(imageView, pMarkdownItem.filename)
        return imageView
    }
}