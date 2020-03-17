/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark;

import com.m2mobi.markymark.item.inline.MarkdownString;

/**
 * Interface used to create an {@link T} using the MarkdownString created by a {@link InlineRule}
 */
public interface InlineDisplayItem<T, O extends MarkdownString> {

	/**
	 * Creates a {@link T} from a MarkdownItem ({@link O}) e.g. (Create a Spannable(T) from a BoldString(O))
	 *
	 * @param pInlineConverter
	 * 		Converter used to convert possible child items
	 * @param pMarkdownString
	 * 		The MarkdownItem used to create a {@link T}
	 * @return Creates a {@link T} using the MarkdownString
	 */
	T create(InlineConverter<T> pInlineConverter, O pMarkdownString);
}