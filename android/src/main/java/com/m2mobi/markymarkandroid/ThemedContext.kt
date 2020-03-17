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
import android.content.ContextWrapper
import androidx.appcompat.view.ContextThemeWrapper

/**
 * Context wrapper that uses the `MarkyMarkTheme` theme
 */
class ThemedContext(pContext: Context) : ContextWrapper(getWrapper(pContext)) {

    companion object {

        /**
         * Wraps a context with a ContextThemeWrapper with the MarkyMark theme
         *
         * @param pContext
         * Context used by the wrapper
         * @return Returns a ContextThemeWrapper with the MarkyMark theme
         */
        private fun getWrapper(pContext: Context): ContextThemeWrapper {
            val array = pContext.theme.obtainStyledAttributes(intArrayOf(R.attr.MarkyMarkTheme))
            val id = array.getResourceId(0, R.style.MarkyMarkStyle)
            array.recycle()

            return ContextThemeWrapper(pContext, id)
        }
    }
}