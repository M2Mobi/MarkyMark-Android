/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark;

import com.m2mobi.markymark.item.MarkdownItem;
import com.m2mobi.markymark.rules.Rule;

/**
 * Interface used to create an {@link T} using the MarkdownItem created by a {@link Rule}
 */
public interface DisplayItem<T, O extends MarkdownItem, INLINE> {

	/**
	 * Creates a {@link T} from a MarkdownItem ({@link O}) e.g. (Create a TextView(T) from a HeaderItem (O))
	 *
	 * @param pMarkdownItem
	 * 		The MarkdownItem used to create a {@link T}
	 * @param pInlineConverter
	 * @return Creates a {@link T} using the MarkdownItem
	 */
	T create(O pMarkdownItem, InlineConverter<INLINE> pInlineConverter);
}