/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark;

import com.m2mobi.markymark.rules.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holder for the markdown that should be rendered
 */
class MarkdownLines {

	/** List of Strings containing the markdown to render */
	private List<String> mLines;

	MarkdownLines(final String pMarkdown) {
		mLines = new ArrayList<>(Arrays.asList(pMarkdown.split("\n")));
	}

	/**
	 * Removes the amount of lines a rule has consumed from markdown that should be rendered
	 *
	 * @param pRule
	 * 		Rule for which the consumed lines should be removed.
	 */
	void removeLinesForRule(final Rule pRule) {
		for (int i = 0; i < pRule.getLinesConsumed(); i++) {
			mLines.remove(0);
		}
	}

	/**
	 * @return true if there are no markdown lines left
	 */
	boolean isEmpty() {
		return mLines.isEmpty();
	}

	/**
	 * Gets the lines of markdown that should be rendered
	 *
	 * @return Returns a List of Strings containing markdown.
	 */
	List<String> getLines() {
		return mLines;
	}

	/**
	 * Returns a given amount of lines
	 *
	 * @param pCount
	 * 		The amount of lines that should be returned
	 * @return List of Strings
	 */
	List<String> getLines(int pCount) {
		return mLines.subList(0, pCount);
	}
}