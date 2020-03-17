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
import com.m2mobi.markymarkcommon.markdownitems.Paragraph;
import com.m2mobi.markymarkcommon.markdownitems.inline.InlineString;

import java.util.List;

/**
 * {@link Rule} that matches paragraphs
 */
public class ParagraphRule implements Rule {

	@Override
	public boolean conforms(final List<String> pMarkdownLines) {
		return true;
	}

	@Override
	public int getLinesConsumed() {
		return 1;
	}

	@Override
	public MarkdownItem toMarkdownItem(final List<String> pMarkdownLines) {
		String line = pMarkdownLines.get(0);
		return new Paragraph(new InlineString(line, true));
	}
}