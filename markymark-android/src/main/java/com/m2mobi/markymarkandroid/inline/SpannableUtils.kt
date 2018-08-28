/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid.inline

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned

import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.item.inline.MarkdownString

/**
 * Utility methods related to [Spannable]s
 */
internal object SpannableUtils {

    /**
     * Creates a Spannable from a MarkdownString
     *
     * @param pInlineConverter
     * InlineConverter used to convert [MarkdownString] to spannable
     * @param pMarkdownString
     * MarkdownString used to create the spannables
     * @return Returns a Spannable
     */
    fun createSpannable(pInlineConverter: InlineConverter<Spanned>, pMarkdownString: MarkdownString): Spannable {
        val stringBuilder = SpannableStringBuilder()
        if (pMarkdownString.hasChildItems()) {
            for (markdownString in pInlineConverter.parseContent(pMarkdownString.content)) {
                stringBuilder.append(pInlineConverter.convert(markdownString))
            }
        } else {
            stringBuilder.append(pMarkdownString.content)
        }
        return stringBuilder
    }
}// no instances