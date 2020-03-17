/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2020 M2mobi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.m2mobi.basemarkdown.rule;

import com.m2mobi.basemarkdown.rule.ListRule;
import com.m2mobi.markymarkcommon.markdownitems.MarkdownList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link ListRule}
 */
public class ListRuleTest {

	private ListRule mListRule = null;

	@BeforeEach
	public void init() {
		mListRule = new ListRule();
	}

	@Test
	public void shouldConformToList() {
		List<String> strings = new ArrayList<>();
		strings.add("1. Number 1");
		assertTrue(mListRule.conforms(strings));

		strings.clear();
		strings.add("- Number 1");
		assertTrue(mListRule.conforms(strings));
	}

	@Test
	public void shouldCreateOrderedList() {
		List<String> strings = new ArrayList<>();
		strings.add("1. Number 1");
		strings.add("2. Number 2");
		strings.add("3. Number 3");
		assertTrue(mListRule.conforms(strings));

		assertTrue(((MarkdownList) mListRule.toMarkdownItem(strings)).isOrdered());
	}

	@Test
	public void shouldCreateUnOrderedList() {
		List<String> strings = new ArrayList<>();
		strings.add("- Number 1");
		strings.add("- Number 2");
		strings.add("- Number 3");
		assertTrue(mListRule.conforms(strings));

		assertFalse(((MarkdownList) mListRule.toMarkdownItem(strings)).isOrdered());
	}

	@Test
	public void shouldCreateCorrectLengthList() {
		List<String> strings = new ArrayList<>();
		strings.add("- Number 1");
		strings.add("- Number 2");
		strings.add("- Number 3");
		assertTrue(mListRule.conforms(strings));

		assertEquals(3, ((MarkdownList) mListRule.toMarkdownItem(strings)).getListItems().size());
	}

	@Test
	public void shouldCreateNestedList() {
		List<String> strings = new ArrayList<>();
		strings.add("- Number 1");
		strings.add("- Number 2");
		strings.add("  - Nest 1");
		strings.add("  - Nest 2");
		assertTrue(mListRule.conforms(strings));

		assertTrue(((MarkdownList) mListRule.toMarkdownItem(strings)).getLastListItem().hasChild());
	}

	@Test
	public void shouldNotCreateNestedList() {
		List<String> strings = new ArrayList<>();
		strings.add("- Number 1");
		strings.add("- Number 2");
		assertTrue(mListRule.conforms(strings));

		assertFalse(((MarkdownList) mListRule.toMarkdownItem(strings)).getLastListItem().hasChild());
	}

	@Test
	public void shouldCreateNestedOrderedList() {
		List<String> strings = new ArrayList<>();
		strings.add("- Number 1");
		strings.add("- Number 2");
		strings.add("  1. Nest 1");
		strings.add("  2. Nest 2");
		assertTrue(mListRule.conforms(strings));

		assertTrue(((MarkdownList) mListRule.toMarkdownItem(strings)).getLastListItem().getChild().get(0).isOrdered());
	}

	@Test
	public void shouldCreateNestedUnorderedList() {
		List<String> strings = new ArrayList<>();
		strings.add("- Number 1");
		strings.add("- Number 2");
		strings.add("  - Nest 1");
		strings.add("  - Nest 2");
		assertTrue(mListRule.conforms(strings));

		assertFalse(((MarkdownList) mListRule.toMarkdownItem(strings)).getLastListItem().getChild().get(0).isOrdered());
	}

	@Test
	public void shouldNotConformToList() {
		List<String> strings = new ArrayList<>();
		strings.add("#. Number 1");
		assertFalse(mListRule.conforms(strings));
	}
}