/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.m2mobi.markymarkandroid.MarkyMarkAndroid
import com.m2mobi.markymarkandroid.MarkyMarkView
import com.m2mobi.markymarkcontentful.ContentfulFlavor
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val markyMarkView = findViewById<MarkyMarkView>(R.id.markymarkview)
        val markyMark = MarkyMarkAndroid.getMarkyMark(this, ContentfulFlavor(), PicassoImageLoader(this))
        markyMarkView.setMarkyMark(markyMark)

        val markdown = loadMarkdownFromAsset(this, "contentful.txt")

        markyMarkView.setMarkdown(markdown)
    }

    private fun loadMarkdownFromAsset(pContext: Context, pFileName: String): String {
        return pContext.assets.open(pFileName).bufferedReader().use(BufferedReader::readText)
    }
}
