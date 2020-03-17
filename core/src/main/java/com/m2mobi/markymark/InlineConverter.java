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

package com.m2mobi.markymark;

import com.m2mobi.markymark.item.inline.MarkdownString;
import com.m2mobi.markymark.item.inline.TextString;
import com.m2mobi.markymark.rules.InlineRule;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Converter for inline markdown items to {@link T}
 */
public class InlineConverter<T> {

	/** HashMap that maps MarkdownString items to display items */
	private final Map<Class<? extends MarkdownString>, InlineDisplayItem<T, MarkdownString>> mMapping = new HashMap<>();

	/** List of InlineRules used to parse MarkdownStrings */
	private List<InlineRule> mInlineRules;

	/**
	 * Sets the inline rules used to parse MarkdownStrings
	 *
	 * @param pInlineRules
	 * 		List of inline rules
	 */
	public void setInlineRules(final List<InlineRule> pInlineRules) {
		mInlineRules = pInlineRules;
	}

	/**
	 * Adds an {@link InlineDisplayItem}
	 *
	 * @param pInlineDisplayItem
	 * 		the item to add.
	 */
	@SuppressWarnings("unchecked")
	public final void addMapping(final InlineDisplayItem pInlineDisplayItem) {
		final Type type = pInlineDisplayItem.getClass().getGenericInterfaces()[0];

		// Get the generic type from the interface
		final Class<T> tClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
		mMapping.put((Class<? extends MarkdownString>) tClass, pInlineDisplayItem);
	}

	/**
	 * Creates a T from the given {@link MarkdownString} using the mapped InlineDisplayItem
	 *
	 * @param pMarkdownString
	 * 		The inline markdown string that will be rendered
	 * @return Returns a {@link T} using the mapped {@link InlineDisplayItem}
	 */
	public T convert(final MarkdownString pMarkdownString) {
		return getDisplayItemForClass(pMarkdownString.getClass()).create(this, pMarkdownString);
	}

	/**
	 * Gets the InlineDisplayItem that is mapped to a MarkdownString
	 *
	 * @param pClass
	 * 		A MarkdownString class
	 * @return Returns the InlineDisplayItem that should be used to convert the MarkdownString to a {@link T}
	 */
	@SuppressWarnings("unchecked")
	private InlineDisplayItem<T, MarkdownString> getDisplayItemForClass(final Class pClass) {
		InlineDisplayItem displayItem = mMapping.get(pClass);
		if (displayItem == null) {
			throw new IllegalStateException("MarkdownItem not mapped to DisplayItem, use Converter.addMapping to add a map for: " +
					pClass.toString());
		}
		return displayItem;
	}

	/**
	 * TODO: Documentation
	 * TODO: consider moving this to a more suitable class?
	 */
	public List<MarkdownString> parseContent(final String pContent) {
		final List<MarkdownString> markdownStrings = new ArrayList<>();

		String toCheck = pContent;
		while (!toCheck.isEmpty()) {
			MatchedRule matchedRule = findFirstMatch(toCheck);
			if (matchedRule != null) {
				if (matchedRule.mStart > 0) {
					markdownStrings.add(new TextString(toCheck.substring(0, matchedRule.mStart), false));
				}

				String content = toCheck.substring(matchedRule.mStart, matchedRule.mEnd);
				markdownStrings.add(matchedRule.mInlineRule.toMarkdownString(content));
				toCheck = toCheck.substring(matchedRule.mEnd, toCheck.length());
			} else if (!toCheck.isEmpty()) {
				markdownStrings.add(new TextString(toCheck, false));
				break;
			}
		}
		return markdownStrings;
	}

	/**
	 * Finds the first InlineRule that matches
	 *
	 * @param pMarkdown
	 * 		String containing markdown
	 * @return Returns a MatchedRule
	 */
	private MatchedRule findFirstMatch(String pMarkdown) {
		MatchedRule currentMatch = null;
		for (InlineRule inlineRule : mInlineRules) {
			final Matcher matcher = inlineRule.getRegex().matcher(pMarkdown);
			if (matcher.find()) {
				MatchedRule matchedRule = new MatchedRule(inlineRule, matcher.start(), matcher.end());
				if (currentMatch == null || currentMatch.isStartLower(matchedRule)) {
					currentMatch = matchedRule;
				}
			}
		}
		return currentMatch;
	}

	/**
	 * Class containing data about a matched rule
	 */
	private static class MatchedRule {

		/** start position of the rule */
		private int mStart;

		/** end position of the rule */
		private int mEnd;

		/** The matching rule */
		private InlineRule mInlineRule;

		private MatchedRule(final InlineRule pInlineRule, final int pStart, final int pEnd) {
			mInlineRule = pInlineRule;
			mStart = pStart;
			mEnd = pEnd;
		}

		/**
		 * TODO: Documentation
		 */
		private boolean isStartLower(final MatchedRule pMatchedRule) {
			return pMatchedRule.mStart < mStart;
		}
	}
}