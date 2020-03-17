/* Copyright (C) M2mobi BV - All Rights Reserved
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Michiel Baneke <baneke@m2mobi.com>, February 2018
*/

package com.m2mobi.markymark.rules;

import com.m2mobi.markymark.item.MarkdownItem;

import java.util.List;

/**
 * Rules are used to identify a piece of Markdown (header, list, paragraph) and convert it to a {@link MarkdownItem}
 */
public interface Rule {

	/**
	 * checks if the passed Markdown conforms to this rule
	 *
	 * @return {@code true} if the Markdown conforms to this rule, {@code false} otherwise.
	 */
	boolean conforms(final List<String> pMarkdownLines);

	/**
	 * @return the amount of lines consumed by this rule
	 */
	int getLinesConsumed();

	/**
	 * @return MarkdownItem that has been created from the Markdown input
	 */
	MarkdownItem toMarkdownItem(final List<String> pMarkdownLines);
}