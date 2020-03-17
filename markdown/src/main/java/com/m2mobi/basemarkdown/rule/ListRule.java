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
import com.m2mobi.markymarkcommon.markdownitems.ListItem;
import com.m2mobi.markymarkcommon.markdownitems.MarkdownList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Rule} that matches lists
 */
public class ListRule implements Rule {

	/** Amount of white space before a nested list item */
	private static final int INDENT_SIZE = 2;

	/** The amount of lines that has been parsed as a markdown list */
	private int mLinesConsumed = 0;

	/** int index of the current line used to keep track of the lines that need to be parsed */
	private int mCurrentLine = 0;

	/** List of markdown strings used to create MarkdownLists */
	private List<String> mMarkdownLines;

	/** Matches an ordered list */
	private static final Pattern ORDERED_LIST_PATTERN = Pattern.compile("^\\s{0,3}\\d+\\.\\s+(.+)$");

	/** Matches an unordered list */
	private static final Pattern UNORDERED_LIST_PATTERN = Pattern.compile("^\\s{0,3}\\-{1}\\s+([^\\*].+)$");

	/** Matches an ordered list and the leading spaces */
	private static final Pattern NESTED_ORDERED_LIST_PATTERN = Pattern.compile("^(\\s*)\\d+\\.\\s+(.+)$");

	/** Matches an unordered list and the leading spaces */
	private static final Pattern NESTED_UNORDERED_LIST_PATTERN = Pattern.compile("^(\\s*)\\-{1}\\s+([^\\*].+)$");

	@Override
	public boolean conforms(final List<String> pMarkdownLines) {
		if (!ORDERED_LIST_PATTERN.matcher(pMarkdownLines.get(0)).matches()
				&& !UNORDERED_LIST_PATTERN.matcher(pMarkdownLines.get(0)).matches()) {
			return false;
		}
		mLinesConsumed = 0;
		for (String line : pMarkdownLines) {
			if (isList(line)) {
				mLinesConsumed++;
			} else {
				return true;
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
		mMarkdownLines = pMarkdownLines;
		mCurrentLine = 0;

		MarkdownList list = new MarkdownList(isOrdered(mMarkdownLines.get(mCurrentLine)), 0);
		parseMarkdownList(list, getNestingLevel(mMarkdownLines.get(mCurrentLine)));

		return list;
	}

	/**
	 * Recursive method that constructs a MarkdownList from Markdown
	 *
	 * @param pMarkdownList
	 * 		Root MarkdownList item
	 * @param currentLevel
	 * 		current nesting level
	 * @return Returns a MarkdownList
	 */
	private void parseMarkdownList(final MarkdownList pMarkdownList, int currentLevel) {
		String line = mMarkdownLines.get(mCurrentLine);
		while (isList(line)) {
			final int level = getNestingLevel(line);
			if (level > currentLevel) {
				// Create sub-list
				final MarkdownList childMarkdownList = new MarkdownList(isOrdered(line), level);
				pMarkdownList.getLastListItem().addChild(childMarkdownList);
				parseMarkdownList(childMarkdownList, level);
			} else if (level == currentLevel) {
				// Add a list item
				pMarkdownList.addListItem(new ListItem(getTextForLine(line)));
				mCurrentLine++;
			} else {
				break;
			}
			if (mCurrentLine >= mMarkdownLines.size()) {
				break;
			}
			line = mMarkdownLines.get(mCurrentLine);
		}
	}

	/**
	 * Determines if the line is an ordered list
	 *
	 * @param pLine
	 * 		String containing a list item
	 * @return Returns true if pLine is an ordered list, false if not
	 */
	private boolean isOrdered(String pLine) {
		return NESTED_ORDERED_LIST_PATTERN.matcher(pLine).matches();
	}

	/**
	 * Determines if a string is part of a list
	 *
	 * @param pLine
	 * 		String containing a possible list
	 * @return Returns true if the string is a ordered or unordered list
	 */
	private boolean isList(String pLine) {
		return NESTED_ORDERED_LIST_PATTERN.matcher(pLine).matches()
				|| NESTED_UNORDERED_LIST_PATTERN.matcher(pLine).matches();
	}

	/**
	 * Gets the nesting level of a list item
	 *
	 * @param pLine
	 * 		String containing a list item
	 * @return Returns an integer representing the nesting level of the list item¶
	 */
	private int getNestingLevel(String pLine) {
		final Matcher matchingRegex = getMatchingMatcher(pLine);
		if (matchingRegex != null) {
			// 1st group contains the whitespace in front of the pattern
			final int level = matchingRegex.group(1).length();
			return level / INDENT_SIZE;
		}
		return 0;
	}

	/**
	 * Extracts the text of a list item using the correct regex group
	 *
	 * @param pLine
	 * 		String that will be matched
	 * @return Returns a String of the text group
	 */
	private String getTextForLine(String pLine) {
		final Matcher matchingRegex = getMatchingMatcher(pLine);
		if (matchingRegex != null) {
			return matchingRegex.group(2).trim();
		}
		return pLine;
	}

	/**
	 * Gets a Matcher that matches a regex pattern
	 *
	 * @param pLine
	 * 		String of the line to match
	 * @return Returns a matching Matcher or null
	 */
	private Matcher getMatchingMatcher(String pLine) {
		Matcher matcher = NESTED_ORDERED_LIST_PATTERN.matcher(pLine);
		if (matcher.matches()) {
			return matcher;
		}

		matcher = NESTED_UNORDERED_LIST_PATTERN.matcher(pLine);
		if (matcher.matches()) {
			return matcher;
		}
		return null;
	}
}