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

package com.m2mobi.markymarkcontentful;

import com.m2mobi.basemarkdown.rule.CodeBlockRule;
import com.m2mobi.basemarkdown.rule.HeaderRule;
import com.m2mobi.basemarkdown.rule.HorizontalLineRule;
import com.m2mobi.basemarkdown.rule.ImageRule;
import com.m2mobi.basemarkdown.rule.ListRule;
import com.m2mobi.basemarkdown.rule.ParagraphRule;
import com.m2mobi.basemarkdown.rule.QuoteRule;
import com.m2mobi.basemarkdown.rule.inline.BoldRule;
import com.m2mobi.basemarkdown.rule.inline.InlineCodeRule;
import com.m2mobi.basemarkdown.rule.inline.ItalicRule;
import com.m2mobi.basemarkdown.rule.inline.LinkRule;
import com.m2mobi.basemarkdown.rule.inline.StrikeRule;
import com.m2mobi.markymark.Flavor;
import com.m2mobi.markymark.rules.InlineRule;
import com.m2mobi.markymark.rules.Rule;

import java.util.ArrayList;
import java.util.List;

import static com.m2mobi.basemarkdown.rule.inline.BoldRule.PATTERN_ASTERISK;
import static com.m2mobi.basemarkdown.rule.inline.BoldRule.PATTERN_UNDERSCORE;

/**
 * Contentful markdown flavor
 */
public class ContentfulFlavor implements Flavor {

	@Override
	public List<Rule> getRules() {
		List<Rule> rules = new ArrayList<>();
		rules.add(new HeaderRule());
		rules.add(new HorizontalLineRule());
		rules.add(new QuoteRule());
		rules.add(new ListRule());
		rules.add(new CodeBlockRule());
		rules.add(new ImageRule());
		return rules;
	}

	@Override
	public List<InlineRule> getInlineRules() {
		List<InlineRule> rules = new ArrayList<>();
		rules.add(new StrikeRule());
		rules.add(new ItalicRule());
		rules.add(new BoldRule(PATTERN_ASTERISK));
		rules.add(new BoldRule(PATTERN_UNDERSCORE));
		rules.add(LinkRule.INSTANCE);
		rules.add(new InlineCodeRule());
		return rules;
	}

	@Override
	public Rule getDefaultRule() {
		return new ParagraphRule();
	}
}