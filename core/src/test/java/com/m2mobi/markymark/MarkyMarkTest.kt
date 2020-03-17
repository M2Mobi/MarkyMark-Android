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

package com.m2mobi.markymark

import com.m2mobi.markymark.rules.Rule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.InputStreamReader
import java.util.stream.Collectors
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class MarkyMarkTest {

    @MockK
    private lateinit var flavor: Flavor

    @MockK
    private lateinit var rule: Rule

    @MockK
    private lateinit var converter: Converter<Any>

    @MockK
    private lateinit var defaultRule: Rule

    private val markyMark
        get() = MarkyMark.Builder<Any>()
            .addFlavor(flavor)
            .addRule(rule)
            .setConverter(converter)
            .setInlineConverter(mockk())
            .setDefaultRule(defaultRule)
            .build()

    private val file: String
        get() {
            val stream = javaClass.getResourceAsStream("/README-light.md")
            val reader = InputStreamReader(stream).buffered()
            return reader.lines().collect(Collectors.joining("\n"))
        }

    @Test
    fun `Test parse default rules`() {
        // Given
        every { rule.conforms(any()) } returns false
        every { defaultRule.linesConsumed } returns 2
        every { defaultRule.toMarkdownItem(any()) } returns mockk()

        // When
        val items = markyMark.parseMarkdown(file)

        // Then
        verify {
            converter.convert(any(), withArg { it is InlineConverter })
        }
        assertTrue { items.isEmpty() }
    }

    @Test
    fun `Test parse custom rules`() {
        // Given
        every { rule.conforms(any()) } returns true
        every { rule.linesConsumed } returns 2
        every { rule.toMarkdownItem(any()) } returns mockk()

        // When
        val items = markyMark.parseMarkdown(file)

        // Then
        verify {
            converter.convert(any(), withArg { it is InlineConverter })
        }
        assertTrue { items.isEmpty() }
    }
}