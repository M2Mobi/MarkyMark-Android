/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid.inline

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.TypefaceSpan

import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.InlineDisplayItem
import com.m2mobi.markymarkcommon.markdownitems.inline.CodeString

/**
 * Converts a [CodeString] to a [Spanned].
 */
class CodeInlineDisplayItem : InlineDisplayItem<Spanned, CodeString> {

    override fun create(pInlineConverter: InlineConverter<Spanned>, pMarkdownString: CodeString): Spanned {
        val spannable = SpannableString(pMarkdownString.content)
        spannable.setSpan(TypefaceSpan("monospace"), 0, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(BackgroundColorSpan(Color.LTGRAY), 0, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}