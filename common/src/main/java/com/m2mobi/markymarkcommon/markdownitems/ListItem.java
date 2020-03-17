/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkcommon.markdownitems;

import com.m2mobi.markymark.item.inline.MarkdownString;
import com.m2mobi.markymarkcommon.markdownitems.inline.InlineString;

import java.util.ArrayList;
import java.util.List;

/**
 * Item in an {@link MarkdownList}
 */
public class ListItem {

	private MarkdownString mItemText;

	private List<MarkdownList> mMarkdownList = new ArrayList<>();

	public ListItem(final String pItemText) {
		mItemText = new InlineString(pItemText, true);
	}

	public boolean hasChild() {
		return mMarkdownList.size() > 0;
	}

	public void addChild(MarkdownList pMarkdownList) {
		mMarkdownList.add(pMarkdownList);
	}

	public List<MarkdownList> getChild() {
		return mMarkdownList;
	}

	public MarkdownString getText() {
		return mItemText;
	}

	@Override
	public String toString() {
		return mItemText.getContent();
	}
}