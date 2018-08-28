/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid.inline

import android.text.Spanned
import android.text.SpannedString

import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.InlineDisplayItem
import com.m2mobi.markymark.item.inline.TextString

import org.apache.commons.lang3.StringEscapeUtils

/**
 * Converts an [TextString] to a [Spanned].
 */
class TextDisplayItem : InlineDisplayItem<Spanned, TextString> {

    override fun create(pInlineConverter: InlineConverter<Spanned>, pMarkdownString: TextString): Spanned {
        val spannable = SpannableUtils.createSpannable(pInlineConverter, pMarkdownString)
        val result = StringEscapeUtils.unescapeHtml4(spannable.toString())
        return SpannedString(result)
    }
}