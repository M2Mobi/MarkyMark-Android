/*
 * Copyright (C) M2mobi BV - All Rights Reserved
 */

package com.m2mobi.markymarkandroid

import android.app.Activity
import android.content.Context
import android.text.Spanned
import android.view.View

import com.m2mobi.markymark.Converter
import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.Flavor
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymark.InlineDisplayItem
import com.m2mobi.markymark.MarkyMark
import com.m2mobi.markymarkandroid.inline.BoldInlineDisplayItem
import com.m2mobi.markymarkandroid.inline.CodeInlineDisplayItem
import com.m2mobi.markymarkandroid.inline.ItalicInlineDisplayItem
import com.m2mobi.markymarkandroid.inline.LinkInlineDisplayItem
import com.m2mobi.markymarkandroid.inline.StrikeInlineDisplayItem
import com.m2mobi.markymarkandroid.inline.StringInlineDisplayItem
import com.m2mobi.markymarkandroid.inline.TextDisplayItem

/**
 * Utility class used to get a [MarkyMark] instance
 */
object MarkyMarkAndroid {

    /**
     * Creates a MarkyMark instance that converts the rules provided by the flavor into MarkyMarkAndroid Views
     *
     * @param activity    Context used to create Views
     * @param pFlavor     Flavor that provides the markdown parsing rules
     * @param imageLoader The ImageLoader that should be used to load images
     * @return MarkMark instance that renders MarkyMarkAndroid Views from Markdown
     */
    fun getMarkyMark(activity: Activity, pFlavor: Flavor, imageLoader: ImageLoader): MarkyMark<View> {
        return getMarkyMark(activity, pFlavor, null, null, imageLoader)
    }

    /**
     * Creates a MarkyMark instance that converts the rules provided by the flavor into MarkyMarkAndroid Views
     *
     * @param activity            Context used to create Views
     * @param pFlavor             Flavor that provides the markdown parsing rules
     * @param pDisplayItems       Extra DisplayItems (replaces DisplayItems that render the same MarkdownItem}
     * @param pInlineDisplayItems Extra InlineDisplayItems (replaces InlineDisplayItems that render the same MarkdownItem}
     * @param imageLoader         The ImageLoader that should be used to load images
     * @return MarkMark instance that renders MarkyMarkAndroid Views from Markdown
     */
    fun getMarkyMark(activity: Activity, pFlavor: Flavor,
                     pDisplayItems: List<DisplayItem<*, *, *>>? = null,
                     pInlineDisplayItems: List<InlineDisplayItem<*, *>>? = null,
                     imageLoader: ImageLoader): MarkyMark<View> {

        val viewConverter = Converter<View>()
        val inlineConverter = InlineConverter<Spanned>()
        inlineConverter.addMapping(BoldInlineDisplayItem())
        inlineConverter.addMapping(StringInlineDisplayItem())
        inlineConverter.addMapping(TextDisplayItem())
        inlineConverter.addMapping(StrikeInlineDisplayItem())
        inlineConverter.addMapping(ItalicInlineDisplayItem())
        inlineConverter.addMapping(LinkInlineDisplayItem())
        inlineConverter.addMapping(CodeInlineDisplayItem())

        val context = ThemedContext(activity)

        viewConverter.addMapping(HeaderDisplayItem(context))
        viewConverter.addMapping(ParagraphDisplayItem(context))
        viewConverter.addMapping(HorizontalRuleDisplayItem(context))
        viewConverter.addMapping(ListDisplayItem(
                context,
                R.dimen.list_margin,
                intArrayOf(R.drawable.bullet_filled, R.drawable.bullet, R.drawable.dash, R.drawable.square))
        )
        viewConverter.addMapping(QuoteDisplayItem(context))
        viewConverter.addMapping(CodeBlockDisplayItem(context))
        viewConverter.addMapping(ImageDisplayItem(context, imageLoader))

        // Add or replace extra inline display items
        if (pInlineDisplayItems != null) {
            for (item in pInlineDisplayItems) {
                inlineConverter.addMapping(item)
            }
        }

        // Add or replace extra display items
        if (pDisplayItems != null) {
            for (item in pDisplayItems) {
                viewConverter.addMapping(item)
            }
        }

        return MarkyMark.Builder<View>()
                .addFlavor(pFlavor)
                .setConverter(viewConverter)
                .setInlineConverter(inlineConverter)
                .build()
    }
}