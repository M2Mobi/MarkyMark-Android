/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.QuoteSpan
import android.view.View
import android.widget.TextView

import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymarkcommon.markdownitems.QuoteBlock

/**
 * Converts an [QuoteBlock] to a [View].
 */
class QuoteDisplayItem(private val mContext: Context) : DisplayItem<View, QuoteBlock, Spanned> {

    override fun create(pMarkdownItem: QuoteBlock, pInlineConverter: InlineConverter<Spanned>): View {
        val textView = TextView(mContext, null, R.attr.MarkdownQuoteStyle)
        val spanned = pInlineConverter.convert(pMarkdownItem.content)
        val spannableString = SpannableString(spanned)
        spannableString.setSpan(QuoteSpan(mContext.resources.getColor(R.color.quote_color)), 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
        return textView
    }
}