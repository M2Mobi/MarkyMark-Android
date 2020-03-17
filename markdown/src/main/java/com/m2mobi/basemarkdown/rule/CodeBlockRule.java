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

package com.m2mobi.basemarkdown.rule;

import com.m2mobi.markymark.item.MarkdownItem;
import com.m2mobi.markymark.rules.Rule;
import com.m2mobi.markymarkcommon.markdownitems.CodeBlock;

import java.util.List;
import java.util.regex.Pattern;

/**
 * {@link Rule} that matches code blocks
 */
public class CodeBlockRule implements Rule {

	/** Pattern used to find the start and end of the code block */
	private static final Pattern CODE_PATTERN_START = Pattern.compile("^`{3}");

	/** Pattern that indicates the code block is a single line */
	private static final Pattern CODE_PATTERN_END_SINGLE_LINE = Pattern.compile("(?<!^)(\\`{3})$");

	/** Pattern that ends the code block */
	private static final Pattern CODE_PATTERN_END = Pattern.compile("(\\`{3})$");

	/** The amount of lines used by this code block */
	private int mLinesConsumed;

	@Override
	public boolean conforms(final List<String> pMarkdownLines) {
		if (!CODE_PATTERN_START.matcher(pMarkdownLines.get(0)).matches()) {
			return false;
		}
		mLinesConsumed = 0;
		for (String line : pMarkdownLines) {
			mLinesConsumed += 1;
			if (mLinesConsumed == 1) {
				if (CODE_PATTERN_END_SINGLE_LINE.matcher(line).matches()) {
					return true;
				}
			} else {
				if (CODE_PATTERN_END.matcher(line).matches()) {
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public int getLinesConsumed() {
		return mLinesConsumed;
	}

	@Override
	public MarkdownItem toMarkdownItem(final List<String> pMarkdownLines) {
		StringBuilder sb = new StringBuilder();
		for (String line : pMarkdownLines) {
			sb.append(line);
			sb.append("\n");
		}

		String content = sb.toString();
		content = content.replaceAll("\n```", "");
		content = content.replaceAll("```\n", "");
		content = content.replaceAll("```", "");

		return new CodeBlock(content);
	}
}