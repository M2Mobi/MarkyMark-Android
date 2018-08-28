/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid.inline

import android.text.Spanned

import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.InlineDisplayItem
import com.m2mobi.markymarkcommon.markdownitems.inline.InlineString

/**
 * Converts an [InlineString] to a [Spanned].
 */
class StringInlineDisplayItem : InlineDisplayItem<Spanned, InlineString> {

    override fun create(pInlineConverter: InlineConverter<Spanned>, pMarkdownString: InlineString): Spanned {
        return SpannableUtils.createSpannable(pInlineConverter, pMarkdownString)
    }
}