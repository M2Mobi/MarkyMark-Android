/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark;

import com.m2mobi.markymark.item.MarkdownItem;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converter for markdown items to {@link T}
 */
public class Converter<T> {

	/** HashMap that maps MarkdownItem items to display items */
	private final Map<Class<? extends MarkdownItem>, DisplayItem<T, MarkdownItem, ?>> mMapping = new HashMap<>();

	/**
	 * Adds an {@link DisplayItem}
	 *
	 * @param pDisplayItem
	 * 		the item to add.
	 */
	@SuppressWarnings("unchecked")
	public final void addMapping(final DisplayItem pDisplayItem) {
		// amazing
		final Type type = pDisplayItem.getClass().getGenericInterfaces()[0];
		final Class<T> tClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
		mMapping.put((Class<? extends MarkdownItem>) tClass, pDisplayItem);
	}

	/**
	 * Creates a List of {@link T} from the given list of {@link MarkdownItem} using the mapped DisplayItem
	 *
	 * @param pMarkdownItems
	 * 		The list of markdown items that will be converted to {@link T}
	 * @return Returns a List of {@link T} using the mapped {@link DisplayItem}
	 */
	@SuppressWarnings("unchecked")
	public List<T> convert(final List<MarkdownItem> pMarkdownItems, InlineConverter pInlineConverter) {
		final List<T> convertedItems = new ArrayList<>(pMarkdownItems.size());
		for (MarkdownItem markdownItem : pMarkdownItems) {
			convertedItems.add((T) getDisplayItemForClass(markdownItem.getClass()).create(markdownItem, pInlineConverter));
		}
		return convertedItems;
	}

	/**
	 * Get a mapped item.
	 *
	 * @param tClass
	 * 		Class to check the map for.
	 * @return Mapped item.
	 */
	DisplayItem getMapping(final Class<? extends MarkdownItem> tClass) {
		return mMapping.get(tClass);
	}

	/**
	 * Gets the DisplayItem that is mapped to a MarkdownItem
	 *
	 * @param pClass
	 * 		A MarkdownItem class
	 * @return Returns the DisplayItem that should be used to convert the MarkdownItem to a {@link T}
	 */
	@SuppressWarnings("unchecked")
	private DisplayItem<T, MarkdownItem, ?> getDisplayItemForClass(final Class pClass) {
		DisplayItem displayItem = mMapping.get(pClass);
		if (displayItem == null) {
			throw new IllegalStateException("MarkdownItem not mapped to DisplayItem, use Converter.addMapping to add a map for: " +
					pClass.toString());
		}
		return displayItem;
	}
}