/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid.inline

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.StyleSpan

import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.InlineDisplayItem
import com.m2mobi.markymarkcommon.markdownitems.inline.ItalicString

/**
 * Converts an [ItalicString] to a [Spanned].
 */
class ItalicInlineDisplayItem : InlineDisplayItem<Spanned, ItalicString> {

    override fun create(pInlineConverter: InlineConverter<Spanned>, pMarkdownString: ItalicString): Spanned {
        val spannable = SpannableUtils.createSpannable(pInlineConverter, pMarkdownString)
        spannable.setSpan(StyleSpan(Typeface.ITALIC), 0, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}