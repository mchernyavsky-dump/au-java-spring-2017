package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.mit.TestUtils.*;

public class PredicateTest {
    @Test
    public void testOr() {
        assertTrue(IS_TRUE.or(ALWAYS_THROW).apply(true));
        assertTrue(IS_FALSE.or(IS_TRUE).apply(true));
        assertFalse(IS_FALSE.or(IS_FALSE).apply(true));
    }

    @Test
    public void testAnd() {
        assertFalse(IS_FALSE.and(ALWAYS_THROW).apply(true));
        assertTrue(IS_TRUE.and(IS_TRUE).apply(true));
        assertFalse(IS_TRUE.and(IS_FALSE).apply(true));
    }

    @Test
    public void testNot() {
        assertTrue(IS_FALSE.not().apply(true));
        assertFalse(IS_TRUE.not().apply(true));
    }

    @Test
    public void testAlwaysTrue() {
        assertTrue(Predicate.ALWAYS_TRUE.apply(null));
    }

    @Test
    public void testAlwaysFalse() {
        assertFalse(Predicate.ALWAYS_FALSE.apply(null));
    }
}
