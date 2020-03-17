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

package com.m2mobi.basemarkdown.rule.inline

import com.m2mobi.markymark.item.inline.MarkdownString
import com.m2mobi.markymark.rules.InlineRule
import com.m2mobi.markymarkcommon.markdownitems.inline.LinkString
import java.util.regex.Pattern

/**
 * Rule for the markdown inline link
 */
object LinkRule : InlineRule {

    /**
     * A Regex to detect links. Ignores if they start with a "!" as those are image links.
     */
    override fun getRegex(): Pattern = Pattern.compile(PATTERN)

    override fun toMarkdownString(content: String): MarkdownString {
        val matcher = regex.matcher(content)
        return if (matcher.find()) {
            LinkString(
                url = matcher.group(2),
                title = matcher.group(3) ?: "",
                content = matcher.group(1)
            )
        } else {
            LinkString()
        }
    }

    private const val PATTERN = "(?<!!)\\[(.+?)]\\((.+?)(?: \"(.+?)\")?\\)"
}