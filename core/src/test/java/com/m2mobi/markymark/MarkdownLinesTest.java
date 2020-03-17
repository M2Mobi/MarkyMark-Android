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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static com.m2mobi.markymark.util.MockKInteropUtil.INSTANCE;
import static io.mockk.MockKKt.every;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MarkdownLinesTest {

    MarkdownLines sot;

    @BeforeEach
    public void setUp() {
        InputStream stream = getClass().getResourceAsStream("/README.md");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader bReader = new BufferedReader(reader);
        String result = bReader.lines().collect(Collectors.joining("\n"));
        sot = new MarkdownLines(result);
    }

    @Test
    public void removeLinesForRule() {
        Rule rule = INSTANCE.mock(Rule.class);
        every(mockKMatcherScope -> rule.getLinesConsumed())
                .returns(20);
        assertEquals(453, sot.getLines().size());
        sot.removeLinesForRule(rule);
        assertEquals(433, sot.getLines().size());
    }

    @Test
    public void isEmpty() {
        assertFalse(sot.isEmpty());
    }

    @Test
    public void getLines() {
        assertEquals(453, sot.getLines().size());
    }

    @Test
    public void getLinesSubset() {
        assertEquals(10, sot.getLines(10).size());
    }
}