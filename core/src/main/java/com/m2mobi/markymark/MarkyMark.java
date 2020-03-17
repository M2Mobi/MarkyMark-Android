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

import com.m2mobi.markymark.item.MarkdownItem;
import com.m2mobi.markymark.rules.InlineRule;
import com.m2mobi.markymark.rules.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * MarkyMark, use the {@link Builder} to create instances
 */
public class MarkyMark<T> {

	/** List of rules that should be used to initialize the parser */
	private List<Rule> mRules = new ArrayList<>();

	/** Rule that should be used as a default fallback */
	private Rule mDefaultRule;

	/** Converter used to convert MarkdownItems to {@link T} */
	private Converter<T> mConverter;

	/** InlineConverter used to convert MarkdownStrings */
	private InlineConverter mInlineConverter;

	/**
	 * Creates a new instance of MarkyMark using the Builders parameters
	 *
	 * @param pBuilder
	 * 		The Builder that should be used to initialise MarkyMark
	 */
	private MarkyMark(final Builder<T> pBuilder) {
		mRules.addAll(pBuilder.mRules);
		mDefaultRule = pBuilder.mDefaultRule;
		mConverter = pBuilder.mConverter;
		mInlineConverter = pBuilder.mInlineConverter;
		mInlineConverter.setInlineRules(pBuilder.mInlineRules);
	}

	/**
	 * Parse the Markdown
	 *
	 * @param pMarkdownString
	 * 		The Markdown String that should be parsed
	 * @return An array of Markdown items
	 */
	public List<T> parseMarkdown(final String pMarkdownString) {
		MarkdownLines markdownLines = new MarkdownLines(pMarkdownString);
		List<MarkdownItem> markdownItems = new ArrayList<>();

		while (!markdownLines.isEmpty()) {
			Rule rule = getRuleForLines(markdownLines.getLines());
			markdownItems.add(rule.toMarkdownItem(markdownLines.getLines(rule.getLinesConsumed())));
			markdownLines.removeLinesForRule(rule);
		}

		return mConverter.convert(markdownItems, mInlineConverter);
	}

	/**
	 * Retrieves the first {@link Rule} that recognizes the syntax of the given lines
	 *
	 * @param pLines
	 * 		The lines that should be checked for syntax
	 * @return The rule that should be used to parse the lines
	 */
	private Rule getRuleForLines(final List<String> pLines) {
		for (Rule rule : mRules) {
			if (rule.conforms(pLines)) {
				return rule;
			}
		}
		return mDefaultRule;
	}

	/**
	 * Basic Builder class to build a MarkyMark object
	 */
	public static class Builder<T> {

		/** List of rules that should be used to initialize the parser */
		private List<Rule> mRules = new ArrayList<>();

		/** Rule that should be used as a default fallback */
		private Rule mDefaultRule;

		/** Converter used to convert MarkdownItems */
		private Converter<T> mConverter;

		/** InlineConverter used to convert MarkdownStrings */
		private InlineConverter mInlineConverter;

		/** InlineRules used to parse MarkdownStrings */
		private List<InlineRule> mInlineRules;

		/**
		 * Set the flavor that should be used to initialize the parser
		 *
		 * @param pFlavor
		 * 		The flavor with rules that should be added
		 * @return This Builder
		 */
		public Builder<T> addFlavor(final Flavor pFlavor) {
			mRules.addAll(pFlavor.getRules());
			mDefaultRule = pFlavor.getDefaultRule();
			mInlineRules = pFlavor.getInlineRules();
			return this;
		}

		/**
		 * Add a {@link Rule} that should be followed when parsing
		 *
		 * @param pRule
		 * 		The rule that should be added
		 * @return This Builder
		 */
		public Builder<T> addRule(final Rule pRule) {
			mRules.add(pRule);
			return this;
		}

		/**
		 * Set the default {@link Rule} that should be used when all other rules fail, Must be called after {@link #addFlavor}
		 *
		 * @param pRule
		 * 		The Rule that should be used as default rule
		 * @return This Builder
		 */
		public Builder<T> setDefaultRule(final Rule pRule) {
			mDefaultRule = pRule;
			return this;
		}

		/**
		 * Set the Converter that should be used to convert MarkdownItems to {@link T}
		 *
		 * @param pConverter
		 * 		The Converter used to convert MarkdownItems
		 * @return This Builder
		 */
		public Builder<T> setConverter(final Converter<T> pConverter) {
			mConverter = pConverter;
			return this;
		}

		/**
		 * Set the InlineConverter that should be used to convert MarkdownStrings
		 *
		 * @param pInlineConverter
		 * 		The Converter used to convert MarkdownStrings
		 * @return This Builder
		 */
		public Builder<T> setInlineConverter(final InlineConverter pInlineConverter) {
			mInlineConverter = pInlineConverter;
			return this;
		}

		/**
		 * Builds an instance of MarkyMark and verifies fields
		 *
		 * @return new instance of MarkyMark
		 */
		public MarkyMark<T> build() {
			if (mRules.isEmpty()) {
				throw new IllegalArgumentException("No rules set, use Builder.addRule() or addFlavor() to add rules");
			}

			if (mDefaultRule == null) {
				throw new IllegalArgumentException("Default rule not set, use Builder.setDefaultRule() to set the default rule.");
			}

			if (mConverter == null) {
				throw new IllegalArgumentException("No Converter set, use Builder.setConverter() to set the converter.");
			}

			if (mInlineConverter == null) {
				throw new IllegalArgumentException("No InlineConverter set, use Builder.setInlineConverter() to set the converter.");
			}

			return new MarkyMark<>(this);
		}
	}
}