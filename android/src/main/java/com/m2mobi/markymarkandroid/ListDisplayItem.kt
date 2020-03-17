/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2020 M2mobi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.m2mobi.markymarkandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.LeadingMarginSpan
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TableLayout.LayoutParams
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.m2mobi.markymark.DisplayItem
import com.m2mobi.markymark.InlineConverter
import com.m2mobi.markymarkcommon.markdownitems.MarkdownList

/**
 * Creates a view containing list items created from a [MarkdownList]
 *
 *
 * User: M2mobi
 * Date 04/05/2016
 * Time 11:31
 */
class ListDisplayItem(private val mContext: Context,
                      @DimenRes pListMargin: Int,
                      @DrawableRes private val mBulletDrawables: IntArray
) : DisplayItem<View, MarkdownList, Spanned> {

    private val mListMargin: Int = mContext.resources.getDimensionPixelSize(pListMargin)

    override fun create(pMarkDownItem: MarkdownList, pInlineConverter: InlineConverter<Spanned>): View {
        return createLayout(pMarkDownItem, pInlineConverter)
    }

    private fun createLayout(pMarkDownItem: MarkdownList, pInlineConverter: InlineConverter<Spanned>): LinearLayout {
        val layout = LinearLayout(mContext)
        layout.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        layout.orientation = LinearLayout.VERTICAL

        pMarkDownItem.listItems.forEachIndexed { index, item ->
            val view = getTextView(pMarkDownItem, pInlineConverter.convert(item.text), index)
            layout.addView(view)

            if (item.hasChild()) {
                for (list in item.child) {
                    val nestedLayout = createLayout(list, pInlineConverter)
                    nestedLayout.setPadding(mListMargin, 0, 0, 0)
                    layout.addView(nestedLayout)
                }
            }
        }

        return layout
    }

    /**
     * Create a drawable resource for the nesting level of this list
     * When there are 3 drawable resources provided it will start with the first again
     * when the nesting level is more than 2 etc.
     *
     * @param pNestedLevel
     * nested level of this list
     * @return item from the array of drawables resources that was provided
     */
    @DrawableRes
    private fun getDrawable(pNestedLevel: Int): Int {
        return mBulletDrawables[pNestedLevel % mBulletDrawables.size]
    }

    /**
     * Creates a TextView that represents a list item
     *
     * @param pMarkDownList
     * MarkDownList used to determine the kind of list
     * @param pText
     * Content of the list item
     * @param pIndex
     * Index of the item in the list
     * @return Returns a TextView
     */
    private fun getTextView(pMarkDownList: MarkdownList, pText: CharSequence, pIndex: Int): TextView {
        val textView = TextView(mContext, null, R.attr.MarkdownListStyle)
        textView.movementMethod = LinkMovementMethod.getInstance()

        if (pMarkDownList.isOrdered) {
            val string = SpannableString(pText)
            string.setSpan(NumberSpan(mListMargin, pIndex + 1), 0, string.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = string
        } else {
            val drawable = mContext.resources.getDrawable(getDrawable(pMarkDownList.nestedLevel))
            val string = SpannableString(pText)
            string.setSpan(BulletSpan(mListMargin, drawable), 0, string.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = string
        }

        return textView
    }

    /**
     * Span that adds a number in front of the first line of a list item
     */
    private inner class NumberSpan(
            /** Width of the margin  */
            private val mGapWidth: Int,
            /** Number of the list item  */
            private val mIndex: Int) : LeadingMarginSpan.LeadingMarginSpan2 {

        override fun drawLeadingMargin(pCanvas: Canvas, pPaint: Paint, pX: Int, pDir: Int, pTop: Int, pBaseline: Int, pBottom: Int,
                                       pText: CharSequence, pStart: Int, pEnd: Int, pFirst: Boolean, pLayout: Layout) {
            if (pFirst) {
                val number = mIndex.toString() + ". "
                val v = pPaint.measureText(number, 0, number.length)
                pCanvas.drawText(number, mGapWidth - v, pBaseline.toFloat(), pPaint)
            }
        }

        override fun getLeadingMargin(first: Boolean): Int {
            return mGapWidth
        }

        override fun getLeadingMarginLineCount(): Int {
            // Only add to first line
            return 1
        }
    }

    /**
     * Span that adds a bullet in front of the first line of a list item
     */
    private inner class BulletSpan(
            /** Width of the margin  */
            private val mGapWidth: Int,
            /** Drawable that will be drawn in front of the first line  */
            private val mDrawable: Drawable) : LeadingMarginSpan.LeadingMarginSpan2 {

        override fun getLeadingMargin(first: Boolean): Int {
            return mGapWidth
        }

        override fun drawLeadingMargin(pCanvas: Canvas, pPaint: Paint, pX: Int, pDir: Int, pTop: Int, pBaseline: Int, pBottom: Int,
                                       pText: CharSequence, pStart: Int, pEnd: Int, pFirst: Boolean, pLayout: Layout) {
            if (!pFirst) {
                return
            }
            val drawableHeight = mDrawable.minimumHeight
            val drawableWidth = mDrawable.minimumWidth
            val y = pBaseline / 2
            val x = mGapWidth / 2

            mDrawable.setBounds(x, y, x + drawableWidth, y + drawableHeight)
            mDrawable.draw(pCanvas)
        }

        override fun getLeadingMarginLineCount(): Int {
            // Only add to first line
            return 1
        }
    }
}