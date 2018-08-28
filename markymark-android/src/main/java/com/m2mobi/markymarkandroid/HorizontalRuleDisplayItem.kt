/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid

import android.content.Context
import android.text.Spanned
import android.view.View
import android.view.ViewGroup.LayoutParams

import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymarkcommon.markdownitems.HorizontalLine

/**
 * Converts an [HorizontalLine] to a [View].
 */
class HorizontalRuleDisplayItem(private val mContext: Context) : DisplayItem<View, HorizontalLine, Spanned> {

    override fun create(pMarkdownItem: HorizontalLine, pInlineConverter: InlineConverter<Spanned>): View {
        val view = View(mContext, null, R.attr.MarkdownHorizontalRuleStyle)
        view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return view
    }
}