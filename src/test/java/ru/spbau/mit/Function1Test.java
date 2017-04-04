package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.mit.TestUtils.*;

public class Function1Test {
    @Test
    public void testApply() {
        assertTrue(NOT.apply(false));
        assertFalse(NOT.apply(true));
    }

    @Test
    public void testCompose() {
        assertTrue(NOT.compose(NOT).apply(true));
        assertFalse(NOT.compose(NOT).apply(false));
    }

    @Test
    public void testComposeToString() {
        assertEquals(NOT.compose(TO_STRING).apply(false), "true");
        assertEquals(NOT.compose(TO_STRING).apply(true), "false");
    }
}
