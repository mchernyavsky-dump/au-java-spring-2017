package ru.spbau.mit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class StringSetTest {
    private StringSetImpl stringSet;

    @Before
    public void setUp() throws Exception {
        stringSet = new StringSetImpl();
    }

    @Test
    public void tesSerializationSimple() {
        assertTrue(stringSet.add("abc"));
        assertTrue(stringSet.add("cde"));
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        stringSet.serialize(outputStream);

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        final StringSetImpl newStringSet = new StringSetImpl();
        newStringSet.deserialize(inputStream);
        assertTrue(newStringSet.contains("abc"));
        assertTrue(newStringSet.contains("cde"));
    }

    @Test
    public void testSerializationEmpty() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        stringSet.serialize(outputStream);

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        final StringSetImpl newStringSet = new StringSetImpl();
        newStringSet.deserialize(inputStream);
        assertEquals(0, newStringSet.size());
    }

    @Test
    public void tesSerializationDeep() {
        assertTrue(stringSet.add(""));
        assertTrue(stringSet.add("a"));
        assertTrue(stringSet.add("abc"));
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        stringSet.serialize(outputStream);

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        final StringSetImpl newStringSet = new StringSetImpl();
        newStringSet.deserialize(inputStream);
        assertTrue(newStringSet.contains(""));
        assertTrue(newStringSet.contains("a"));
        assertFalse(newStringSet.contains("ab"));
        assertTrue(newStringSet.contains("abc"));
    }


    @Test(expected = SerializationException.class)
    public void testSerializationFails() {
        byte[] buf = {0};
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
        final StringSetImpl newStringSet = new StringSetImpl();
        newStringSet.deserialize(inputStream);
        fail();
    }
}
