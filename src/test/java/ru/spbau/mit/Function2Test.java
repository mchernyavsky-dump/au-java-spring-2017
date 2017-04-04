package ru.spbau.mit;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static ru.spbau.mit.TestUtils.*;

@RunWith(JUnitParamsRunner.class)
public class Function2Test {
    @Test
    @Parameters(method = "andTruthTable")
    public void testApply(final boolean arg1, final boolean arg2, final boolean expected) {
        final Boolean actual = AND.apply(arg1, arg2);
        assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "andTruthTable")
    public void testCompose(final boolean arg1, final boolean arg2, final boolean negExpected) {
        final Boolean actual = AND.compose(NOT).apply(arg1, arg2);
        assertEquals(!negExpected, actual);
    }

    @Test
    @Parameters(method = "andTruthTable")
    public void testBind1(final boolean arg1, final boolean arg2, final boolean expected) {
        final Boolean actual = AND.bind1(arg1).apply(arg2);
        assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "andTruthTable")
    public void testBind2(final boolean arg1, final boolean arg2, final boolean expected) {
        final Boolean actual = AND.bind2(arg2).apply(arg1);
        assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "andTruthTable")
    public void testCurry(final boolean arg1, final boolean arg2, final boolean expected) {
        final Boolean actual = AND.curry().apply(arg1).apply(arg2);
        assertEquals(expected, actual);
    }

    @NotNull
    private Object[] andTruthTable() {
        return new Object[]{
                new Object[]{false, false, false},
                new Object[]{false, true, false},
                new Object[]{true, false, false},
                new Object[]{true, true, true}
        };
    }
}
