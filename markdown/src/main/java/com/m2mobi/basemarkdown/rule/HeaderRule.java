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
import com.m2mobi.markymark.rules.RegexRule;
import com.m2mobi.markymark.rules.Rule;
import com.m2mobi.markymarkcommon.markdownitems.Header;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Rule} that matches headers from H1 to H6
 */
public class HeaderRule extends RegexRule {

	/** Pattern used to find headers and the header size */
	private static final Pattern HEADER_PATTERN = Pattern.compile("^(#{1,6})\\s*(.+)$");

	@Override
	protected Pattern getRegex() {
		return HEADER_PATTERN;
	}

	@Override
	public MarkdownItem toMarkdownItem(final List<String> pMarkdownLines) {
		String headerContent = "";
		int headerType = 1;
		Matcher headerMatcher = HEADER_PATTERN.matcher(pMarkdownLines.get(0));
		if (headerMatcher.matches()) {
			headerType = headerMatcher.group(1).length();
			headerContent = headerMatcher.group(2);
		}
		return new Header(headerContent, headerType);
	}
}