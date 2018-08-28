/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid

import android.content.Context
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView

import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymarkcommon.markdownitems.Paragraph

/**
 * Converts an [Paragraph] to a [View].
 */
class ParagraphDisplayItem(private val mContext: Context) : DisplayItem<View, Paragraph, Spanned> {

    override fun create(pMarkdownItem: Paragraph, pInlineConverter: InlineConverter<Spanned>): View {
        val textView = TextView(mContext, null, R.attr.MarkdownParagraphStyle)
        textView.movementMethod = LinkMovementMethod.getInstance()
        val convert = pInlineConverter.convert(pMarkdownItem.content)
        textView.text = convert
        return textView
    }
}