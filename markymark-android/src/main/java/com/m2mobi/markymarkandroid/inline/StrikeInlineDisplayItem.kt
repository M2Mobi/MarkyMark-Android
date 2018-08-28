/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid.inline

import android.text.Spanned
import android.text.style.StrikethroughSpan

import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.InlineDisplayItem
import com.m2mobi.markymarkcommon.markdownitems.inline.StrikeString

/**
 * Converts a [StrikeString] to a [Spanned].
 */
class StrikeInlineDisplayItem : InlineDisplayItem<Spanned, StrikeString> {

    override fun create(pInlineConverter: InlineConverter<Spanned>, pMarkdownString: StrikeString): Spanned {
        val spannable = SpannableUtils.createSpannable(pInlineConverter, pMarkdownString)
        spannable.setSpan(StrikethroughSpan(), 0, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}