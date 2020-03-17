/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid

import android.content.Context
import android.text.Spanned
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TextView

import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymarkcommon.markdownitems.CodeBlock

/**
 * Converts an [CodeBlock] to a [View].
 */
class CodeBlockDisplayItem(private val mContext: Context) : DisplayItem<View, CodeBlock, Spanned> {

    override fun create(pMarkdownItem: CodeBlock, pInlineConverter: InlineConverter<Spanned>): View {
        val textView = TextView(mContext, null, R.attr.MarkdownCodeStyle)
        textView.setHorizontallyScrolling(true)
        textView.text = pMarkdownItem.content

        val scrollView = HorizontalScrollView(mContext)
        scrollView.addView(textView)
        return scrollView
    }
}