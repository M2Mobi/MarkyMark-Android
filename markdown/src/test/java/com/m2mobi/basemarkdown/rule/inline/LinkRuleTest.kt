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

import com.m2mobi.markymarkcommon.markdownitems.inline.LinkString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LinkRuleTest {

    private val content = "some link text"

    private val link = "https://somewebsite.com/path/subPath/file.ext"

    @Test
    fun `When a valid link without a title is supplied, return the correct markdown link`() {
        // Given
        val mdLink = "[$content]($link)"

        // When
        val result = LinkRule.toMarkdownString(mdLink)

        // Then
        assertTrue(result is LinkString)
        assertEquals(expected = content, actual = result.content)
        assertFalse(result.isChildrenEnabled)
        assertEquals(expected = link, actual = result.url)
        assertTrue(result.title.isEmpty())
    }

    @Test
    fun `When a valid link with a title is supplied, return the correct markdown link`() {
        // Given
        val title = "some fun title"
        val mdLink = "[$content]($link \"$title\")"

        // When
        val result = LinkRule.toMarkdownString(mdLink)

        // Then
        assertTrue(result is LinkString)
        assertFalse(result.isChildrenEnabled)
        assertEquals(expected = content, actual = result.content)
        assertEquals(expected = link, actual = result.url)
        assertEquals(expected = title, actual = result.title)
    }

    @Test
    fun `When a image link is supplied, ignore`() {
        // Given
        val mdLink = "![koala image]" +
                "(https://4.bp.blogspot.com/_0P7ZCnnT_b8/S_8h6we7HeI/AAAAAAAAACY/8llBx-IwYfs/s1600/koala2.jpg)"

        // When
        val result = LinkRule.toMarkdownString(mdLink)

        // Then
        assertTrue(result is LinkString)
        assertTrue(result.content.isEmpty())
        assertTrue(result.url.isEmpty())
        assertTrue(result.title.isEmpty())
        assertFalse(result.isChildrenEnabled)
    }

    @Test
    fun `When an image link is preceded by a space, ignore`() {
        // Given
        val mdLink = "some text ![koala image]" +
                "(https://4.bp.blogspot.com/_0P7ZCnnT_b8/S_8h6we7HeI/AAAAAAAAACY/8llBx-IwYfs/s1600/koala2.jpg)"

        // When
        val result = LinkRule.toMarkdownString(mdLink)

        // Then
        assertTrue(result is LinkString)
        assertTrue(result.content.isEmpty())
        assertTrue(result.url.isEmpty())
        assertTrue(result.title.isEmpty())
        assertFalse(result.isChildrenEnabled)
    }

    @Test
    fun `When a link is preceded by text, return the correct markdown link`() {
        // Given
        val mdText = "Some thing interesting: [$content]($link)"

        // When
        val result = LinkRule.toMarkdownString(mdText)

        // Then
        assertTrue(result is LinkString)
        assertEquals(expected = content, actual = result.content)
        assertFalse(result.isChildrenEnabled)
        assertEquals(expected = link, actual = result.url)
        assertTrue(result.title.isEmpty())
    }

    @Test
    fun `When the previous sentence ends with a ! and a link follow, return the correct markdown link`() {
        // Given
        val mdText = "Some thing really cool! [$content]($link)"

        // When
        val result = LinkRule.toMarkdownString(mdText)

        // Then
        assertTrue(result is LinkString)
        assertEquals(expected = content, actual = result.content)
        assertFalse(result.isChildrenEnabled)
        assertEquals(expected = link, actual = result.url)
        assertTrue(result.title.isEmpty())
    }

    @Test
    fun `When a link is surrounded by text, return the correct markdown link`() {
        // Given
        val mdText = "Some thing interesting [$content]($link) some thing else interesting"

        // When
        val result = LinkRule.toMarkdownString(mdText)

        // Then
        assertTrue(result is LinkString)
        assertEquals(expected = content, actual = result.content)
        assertFalse(result.isChildrenEnabled)
        assertEquals(expected = link, actual = result.url)
        assertTrue(result.title.isEmpty())
    }
}