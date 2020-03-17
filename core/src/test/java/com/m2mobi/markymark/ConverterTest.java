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

package com.m2mobi.markymark;

import com.m2mobi.markymark.item.MarkdownItem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.m2mobi.markymark.util.MockKInteropUtil.INSTANCE;
import static io.mockk.MockKKt.every;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConverterTest {

    private Converter<Object> sut;

    @BeforeEach
    public void setUp() {
        sut = new Converter<>();
    }

    @AfterEach
    public void testMapping() {
        assertTrue(sut.getMapping(MockDisplayItem.class) instanceof MockDisplayItem);
    }

    @Test
    public void addMapping() {
        MockDisplayItem displayItem = new MockDisplayItem();
        sut.addMapping(displayItem);
    }

    @Test
    public void convertFailsOnMismatchedObject() {
        MockDisplayItem displayItem = new MockDisplayItem();
        sut.addMapping(displayItem);
        List<MarkdownItem> mockList = new ArrayList<>();
        MarkdownItem mockItem = INSTANCE.mock(MarkdownItem.class);
        mockList.add(mockItem);
        InlineConverter mockConverter = INSTANCE.mock(InlineConverter.class);
        sut.convert(mockList, mockConverter);
    }

    @Test
    public void convertSucceeds() {
        LinkedList linkedList = INSTANCE.mock(LinkedList.class);
        MockDisplayItem displayItem = new MockDisplayItem(linkedList);
        List<MarkdownItem> mockList = new ArrayList<>();
        mockList.add(displayItem);
        InlineConverter mockConverter = INSTANCE.mock(InlineConverter.class);
        every(mockKMatcherScope -> linkedList.getFirst())
                .returns(new Object());

        sut.addMapping(displayItem);
        assertTrue(sut.getMapping(MockDisplayItem.class) instanceof MockDisplayItem);
        List<Object> converted = sut.convert(mockList, mockConverter);
        for (Object object : converted) {
            assertNotNull(object);
        }
        assertEquals(converted.size(), 1);
    }

    static class MockDisplayItem implements DisplayItem<Object, MockDisplayItem, InlineConverter>, MarkdownItem {
        private LinkedList list;

        MockDisplayItem() {
            this(new LinkedList());
            list.add(null);
        }

        MockDisplayItem(LinkedList list) {
            this.list = list;
        }

        @Override
        public Object create(MockDisplayItem pMarkdownItem, InlineConverter pInlineConverter) {
            return list.getFirst();
        }
    }
}
