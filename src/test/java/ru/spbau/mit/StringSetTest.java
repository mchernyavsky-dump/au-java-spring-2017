package ru.spbau.mit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;

public class StringSetTest {
    private StringSetImpl stringSet;
    private StringSetImpl deserializedStringSet;
    private InputStream input;
    private OutputStream output;

    @Before
    public void setUp() throws Exception {
        stringSet = new StringSetImpl();
        deserializedStringSet = new StringSetImpl();
        final Pipe pipe = Pipe.open();
        input = Channels.newInputStream(pipe.source());
        output = Channels.newOutputStream(pipe.sink());
    }

    @After
    public void tearDown() throws Exception {
        input.close();
        output.close();
    }

    @Test
    public void testSerializationSimple() throws IOException {
        assertTrue(stringSet.add("abc"));
        assertTrue(stringSet.add("cde"));
        stringSet.serialize(output);
        output.close();
        deserializedStringSet.deserialize(input);
        assertTrue(deserializedStringSet.contains("abc"));
        assertTrue(deserializedStringSet.contains("cde"));
        assertEquals(stringSet.size(), deserializedStringSet.size());
    }

    @Test
    public void testSerializationEmpty() throws IOException {
        stringSet.serialize(output);
        output.close();
        deserializedStringSet.deserialize(input);
        assertEquals(stringSet.size(), deserializedStringSet.size());
    }

    @Test
    public void testSerializationDeep() throws IOException {
        assertTrue(stringSet.add(""));
        assertTrue(stringSet.add("a"));
        assertTrue(stringSet.add("abc"));
        stringSet.serialize(output);
        output.close();
        deserializedStringSet.deserialize(input);
        assertTrue(deserializedStringSet.contains(""));
        assertTrue(deserializedStringSet.contains("a"));
        assertFalse(deserializedStringSet.contains("ab"));
        assertTrue(deserializedStringSet.contains("abc"));
        assertEquals(stringSet.size(), deserializedStringSet.size());
    }


    @Test(expected = SerializationException.class)
    public void testSerializationFails() throws IOException {
        output.close();
        deserializedStringSet.deserialize(input);
        fail();
    }
}
