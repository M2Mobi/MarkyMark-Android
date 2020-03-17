/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid.inline

import android.text.Spanned
import android.text.style.URLSpan

import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.InlineDisplayItem
import com.m2mobi.markymarkcommon.markdownitems.inline.LinkString

/**
 * Converts a [LinkString] to a [Spanned].
 */
class LinkInlineDisplayItem : InlineDisplayItem<Spanned, LinkString> {

    override fun create(pInlineConverter: InlineConverter<Spanned>, pMarkdownString: LinkString): Spanned {
        val spannable = SpannableUtils.createSpannable(pInlineConverter, pMarkdownString)
        spannable.setSpan(URLSpan(pMarkdownString.url), 0, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}