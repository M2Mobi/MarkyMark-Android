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

import com.m2mobi.markymark.rules.Rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static com.m2mobi.markymark.util.MockKInteropUtil.INSTANCE;

/**
 * Tests for the markymark builder.
 */
public class MarkyMarkBuildTest {

    @Test
    public void buildInstance() {
        MarkyMark.Builder builder = new MarkyMark.Builder();
        Flavor flavor = INSTANCE.mock(Flavor.class);
        Rule rule = INSTANCE.mock(Rule.class);
        Converter converter = INSTANCE.mock(Converter.class);
        InlineConverter inlineConverter = INSTANCE.mock(InlineConverter.class);
        MarkyMark markyMark = builder.addFlavor(flavor)
                .addRule(rule)
                .setConverter(converter)
                .setInlineConverter(inlineConverter)
                .setDefaultRule(rule)
                .build();

        assertNotNull(markyMark);
    }

    @Test
    public void buildInstanceFailsOnEmptyRules() {
        MarkyMark.Builder builder = new MarkyMark.Builder();
        Flavor flavor = INSTANCE.mock(Flavor.class);
        Rule rule = INSTANCE.mock(Rule.class);
        Converter converter = INSTANCE.mock(Converter.class);
        InlineConverter inlineConverter = INSTANCE.mock(InlineConverter.class);
        try {
            builder.addFlavor(flavor)
                    .setConverter(converter)
                    .setInlineConverter(inlineConverter)
                    .setDefaultRule(rule)
                    .build();
            fail("No exception");
        } catch (IllegalArgumentException e) {
            assertEquals("No rules set, use Builder.addRule() or addFlavor() to add rules", e.getMessage());
        }
    }

    @Test
    public void buildInstanceFailsOnEmptyDefaultRule() {
        MarkyMark.Builder builder = new MarkyMark.Builder();
        Flavor flavor = INSTANCE.mock(Flavor.class);
        Rule rule = INSTANCE.mock(Rule.class);
        Converter converter = INSTANCE.mock(Converter.class);
        InlineConverter inlineConverter = INSTANCE.mock(InlineConverter.class);
        try {
            builder.addFlavor(flavor)
                    .addRule(rule)
                    .setConverter(converter)
                    .setInlineConverter(inlineConverter)
                    .build();
            fail("No exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Default rule not set, use Builder.setDefaultRule() to set the default rule.", e.getMessage());
        }
    }

    @Test
    public void buildInstanceFailsOnEmptyConverter() {
        MarkyMark.Builder builder = new MarkyMark.Builder();
        Flavor flavor = INSTANCE.mock(Flavor.class);
        Rule rule = INSTANCE.mock(Rule.class);
        InlineConverter inlineConverter = INSTANCE.mock(InlineConverter.class);
        try {
            builder.addFlavor(flavor)
                    .addRule(rule)
                    .setDefaultRule(rule)
                    .setInlineConverter(inlineConverter)
                    .build();
            fail("No exception");
        } catch (IllegalArgumentException e) {
            assertEquals("No Converter set, use Builder.setConverter() to set the converter.", e.getMessage());
        }
    }

    @Test
    public void buildInstanceFailsOnEmptyInlineConverter() {
        MarkyMark.Builder builder = new MarkyMark.Builder();
        Flavor flavor = INSTANCE.mock(Flavor.class);
        Rule rule = INSTANCE.mock(Rule.class);
        Converter converter = INSTANCE.mock(Converter.class);
        try {
            builder.addFlavor(flavor)
                    .addRule(rule)
                    .setDefaultRule(rule)
                    .setConverter(converter)
                    .build();
            fail("No exception");
        } catch (IllegalArgumentException e) {
            assertEquals("No InlineConverter set, use Builder.setInlineConverter() to set the converter.", e.getMessage());
        }
    }
}