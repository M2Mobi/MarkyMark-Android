/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid

import android.content.Context
import android.text.Spanned
import android.view.View
import android.widget.TextView

import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymarkcommon.markdownitems.Header

/**
 * Converts an [Header] to a [View].
 */
class HeaderDisplayItem(private val mContext: Context) : DisplayItem<View, Header, Spanned> {

    override fun create(pMarkdownItem: Header, pInlineConverter: InlineConverter<Spanned>): View {
        val textView = TextView(mContext, null, attr[pMarkdownItem.header - 1])
        textView.text = pInlineConverter.convert(pMarkdownItem.content)
        return textView
    }

    companion object {

        private val attr = intArrayOf(
                R.attr.MarkdownHeader1Style,
                R.attr.MarkdownHeader2Style,
                R.attr.MarkdownHeader3Style,
                R.attr.MarkdownHeader4Style,
                R.attr.MarkdownHeader5Style,
                R.attr.MarkdownHeader6Style
        )
    }
}