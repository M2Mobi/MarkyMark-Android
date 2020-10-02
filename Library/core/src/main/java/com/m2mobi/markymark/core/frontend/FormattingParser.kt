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

package com.m2mobi.markymark.core.frontend

import com.m2mobi.markymark.core.model.FormattedText
import com.m2mobi.markymark.core.model.Formatting
import com.m2mobi.markymark.core.model.FormattingResult

/**
 * Used for retrieving formatting from a line of text.
 *
 * This class transforms a line of text to a "clean" state and delegates the responsibility for parsing the formattings
 * to the [rules].
 */
class FormattingParser(private val rules: List<FormattingRule>) : Parser<FormattedText> {

    override fun parse(text: String): FormattedText {
        return if (text.isNotBlank()) {
            var progress = CleaningProgress(text)
            for (rule in rules) {
                progress = processMatches(results = rule.parse(progress.remainingLine), progress = progress)
            }

            progress.toFormattedText()
        } else {
            FormattedText(cleanString = text)
        }
    }

    private tailrec fun processMatches(
        results: List<FormattingResult>,
        progress: CleaningProgress
    ): CleaningProgress {
        val result = results.firstOrNull() ?: return progress
        val (remainingLine, formattings) = progress

        val content = remainingLine
            .substring(result.formatting.range)
            .removeSurrounding(prefix = result.prefix, suffix = result.suffix)
        val cleanedLine = remainingLine.replaceRange(result.formatting.range, content)

        return processMatches(
            results = results.drop(1).map { it.copy(formatting = it.formatting.maybeOffset(result)) },
            progress = CleaningProgress(
                remainingLine = cleanedLine,
                formattings = formattings.plus(result.formatting).map { it.maybeOffset(result) }
            )
        )
    }

    private fun Formatting<*>.maybeOffset(result: FormattingResult): Formatting<*> {
        return copy(range = range.maybeOffset(result))
    }

    private fun IntRange.maybeOffset(result: FormattingResult): IntRange {
        return first.maybeOffset(result)..last.maybeOffset(result)
    }

    private fun Int.maybeOffset(result: FormattingResult): Int {
        var newIndex = this
        if (this >= result.formatting.range.first) {
            newIndex -= result.prefix.length
        }
        if (this >= result.formatting.range.last) {
            newIndex -= result.suffix.length
        }
        return newIndex
    }

    private data class CleaningProgress(
        val remainingLine: String,
        val formattings: List<Formatting<*>> = emptyList()
    ) {

        fun toFormattedText(): FormattedText {
            return FormattedText(cleanString = remainingLine, formattings = formattings)
        }
    }
}
