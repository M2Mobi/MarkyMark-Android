/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymarkandroid;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.QuoteSpan;
import android.view.View;
import android.widget.TextView;

import com.m2mobi.markymark.DisplayItem;
import com.m2mobi.markymark.InlineConverter;
import com.m2mobi.markymarkcommon.markdownitems.QuoteBlock;

/**
 * Converts an {@link QuoteBlock} to a {@link View}.
 */
public class QuoteDisplayItem implements DisplayItem<View, QuoteBlock, Spanned> {

	private Context mContext;

	public QuoteDisplayItem(final Context pContext) {
		mContext = pContext;
	}

	@Override
	public View create(final QuoteBlock pMarkdownItem, final InlineConverter<Spanned> pInlineConverter) {
		TextView textView = new TextView(mContext, null, R.attr.MarkdownQuoteStyle);
		final Spanned spanned = pInlineConverter.convert(pMarkdownItem.getContent());
		final SpannableString spannableString = new SpannableString(spanned);
		spannableString.setSpan(new QuoteSpan(mContext.getResources().getColor(R.color.quote_color)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(spannableString);
		return textView;
	}
}