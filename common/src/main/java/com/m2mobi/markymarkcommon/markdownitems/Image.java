/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkcommon.markdownitems;

import com.m2mobi.markymark.item.MarkdownItem;

/**
 * {@link MarkdownItem} used for storing information to show an image
 */
public class Image implements MarkdownItem {

	private final String mFilename;

	private final String mAltText;

	public Image(String pFilename, String pAltText) {
		mFilename = pFilename;
		mAltText = pAltText;
	}

	public String getAltText() {
		return mAltText;
	}

	public String getFilename() {
		return mFilename;
	}
}