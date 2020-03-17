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

package com.m2mobi.basemarkdown.rule.inline;

import com.m2mobi.markymark.item.inline.MarkdownString;
import com.m2mobi.markymark.rules.InlineRule;
import com.m2mobi.markymarkcommon.markdownitems.inline.BoldString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link InlineRule} that matches bold text
 */
public class BoldRule implements InlineRule {

	public final static String PATTERN_ASTERISK = "*";
	public final static String PATTERN_UNDERSCORE = "_";

	/** Regex pattern used to match bold strings */
	private final Pattern mBoldPattern;

	/**
	 * Creates a new BoldRule that matches on the given pattern e.g. ('*' or '_')
	 *
	 * @param pPattern
	 * 		The pattern to use
	 */
	public BoldRule(final String pPattern) {
		final String escaped = Pattern.quote(pPattern);
		mBoldPattern = Pattern.compile("(" + escaped + "{2})(.+?)(" + escaped + "{2})(?!" + escaped + ")");
	}

	@Override
	public Pattern getRegex() {
		return mBoldPattern;
	}

	@Override
	public MarkdownString toMarkdownString(String pContent) {
		Matcher matcher = getRegex().matcher(pContent);
		String content = "";
		if (matcher.find()) {
			content = matcher.group(2);
		}
		return new BoldString(content, true);
	}
}