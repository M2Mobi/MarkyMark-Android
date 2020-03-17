/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark.rules;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Basic rule implementation that checks if the first line matches the given regex.
 */
public abstract class RegexRule implements Rule {

	protected abstract Pattern getRegex();

	@Override
	public boolean conforms(final List<String> pMarkdownLines) {
		return getRegex().matcher(pMarkdownLines.get(0)).matches();
	}

	@Override
	public int getLinesConsumed() {
		// Default to 1 as this is a single line block
		return 1;
	}
}