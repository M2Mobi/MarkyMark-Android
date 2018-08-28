/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid

import android.content.Context
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.support.v7.view.ContextThemeWrapper

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