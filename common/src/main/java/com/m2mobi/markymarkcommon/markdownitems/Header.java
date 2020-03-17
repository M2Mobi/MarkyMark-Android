/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkcommon.markdownitems;

import com.m2mobi.markymark.item.MarkdownItem;
import com.m2mobi.markymark.item.inline.MarkdownString;
import com.m2mobi.markymarkcommon.markdownitems.inline.InlineString;

/**
 * {@link MarkdownItem} used for storing text inside a header
 */
public class Header implements MarkdownItem {

	private final MarkdownString mContent;

	private final int mHeader;

	public Header(final String pContent, final int pHeader) {
		mContent = new InlineString(pContent, true);
		mHeader = pHeader;
	}

	public MarkdownString getContent() {
		return mContent;
	}

	public int getHeader() {
		return mHeader;
	}
}