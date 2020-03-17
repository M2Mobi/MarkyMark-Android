/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark;

import com.m2mobi.markymark.rules.InlineRule;
import com.m2mobi.markymark.rules.Rule;

import java.util.List;

/**
 * Flavor interface, A flavor contains all the rules that can be used to parse the markdown.
 * A flavor can be used to parse different markdown standards (Github Flavored, Contentful, etc.)
 */
public interface Flavor {

	/**
	 * @return List of {@link Rule} that are supported by this flavor
	 */
	List<Rule> getRules();

	/**
	 * @return Returns a list of {@link InlineRule} that are supported by this flavor
	 */
	List<InlineRule> getInlineRules();

	/**
	 * @return Returns the default Rule that should be used if no matching rule can be found for a piece of markdown
	 */
	Rule getDefaultRule();
}