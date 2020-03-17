/*
 * Copyright (C) M2mobi BV - All Rights Reserved
 */

package com.m2mobi.markymarkandroid

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

import com.m2mobi.markymark.MarkyMark

/**
 * Convenience view that can be used to display Markdown
 */
class MarkyMarkView : LinearLayout {

    private var mMarkyMark: MarkyMark<View>? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @SuppressLint("NewApi")
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    /**
     * Set the MarkyMark instance that will be used to parse the Markdown content
     * @param pMarkyMark instance of MarkyMark that will be used
     */
    fun setMarkyMark(pMarkyMark: MarkyMark<View>) {
        mMarkyMark = pMarkyMark
    }

    /**
     * Set the Markdown that should be displayed in this view
     *
     * @param pMarkdownString The String that should be parsed and displayed
     */
    fun setMarkdown(pMarkdownString: String) {
        removeAllViews()

        if (mMarkyMark != null) {
            val views = mMarkyMark!!.parseMarkdown(pMarkdownString)
            for (view in views) {
                addView(view)
            }
        } else {
            throw IllegalStateException("No instance of MarkyMark provided")
        }
    }
}
