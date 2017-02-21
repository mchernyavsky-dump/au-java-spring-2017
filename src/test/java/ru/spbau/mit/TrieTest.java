package ru.spbau.mit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrieTest {
    private Trie trie;

    @Before
    public void setUp() throws Exception {
        trie = new Trie();
    }

    @Test
    public void testAddOneString() throws Exception {
        assertTrue(trie.add("string"));
        assertTrue(trie.contains("string"));
        assertEquals(1, trie.size());
    }

    @Test
    public void testAddTwoStrings() throws Exception {
        assertTrue(trie.add("firstString"));
        assertTrue(trie.add("secondString"));
        assertTrue(trie.contains("firstString"));
        assertTrue(trie.contains("secondString"));
        assertEquals(2, trie.size());
    }

    @Test
    public void testAddOneStringTwice() throws Exception {
        assertTrue(trie.add("string"));
        assertFalse(trie.add("string"));
        assertTrue(trie.contains("string"));
        assertEquals(1, trie.size());
    }

    @Test
    public void testContains() throws Exception {
        assertFalse(trie.contains("firstString"));
        trie.add("firstString");
        assertTrue(trie.contains("firstString"));
        assertFalse(trie.contains("secondString"));
    }

    @Test
    public void testRemove() throws Exception {
        assertFalse(trie.remove("string"));
        trie.add("string");
        assertTrue(trie.remove("string"));
        assertFalse(trie.contains("string"));
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(0, trie.size());

        trie.add("firstString");
        assertEquals(1, trie.size());
        trie.add("secondString");
        assertEquals(2, trie.size());
        trie.add("secondString");
        assertEquals(2, trie.size());

        trie.remove("secondString");
        assertEquals(1, trie.size());
        trie.remove("secondString");
        assertEquals(1, trie.size());
        trie.remove("firstString");
        assertEquals(0, trie.size());
    }

    @Test
    public void testHowManyStartsWithPrefix() throws Exception {
        trie.add("a");
        trie.add("aa");
        trie.add("ab");
        trie.add("aaa");

        assertEquals(trie.size(), trie.howManyStartsWithPrefix(""));
        assertEquals(trie.size(), trie.howManyStartsWithPrefix("a"));
        assertEquals(2, trie.howManyStartsWithPrefix("aa"));
        assertEquals(1, trie.howManyStartsWithPrefix("ab"));
        assertEquals(1, trie.howManyStartsWithPrefix("aaa"));
        assertEquals(0, trie.howManyStartsWithPrefix("aba"));
    }

    @Test
    public void testEmptyString() throws Exception {
        assertFalse(trie.contains(""));
        assertTrue(trie.add(""));
        assertTrue(trie.contains(""));
        assertEquals(1, trie.size());
        assertTrue(trie.remove(""));
        assertFalse(trie.contains(""));
        assertEquals(0, trie.size());
    }

    @Test
    public void testLegalCharactersInString() throws Exception {
        final String lowerCaseLatin = "abcdefghijklmnopqrstuvwxyz";
        trie.add(lowerCaseLatin);

        final String upperCaseLatin = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        trie.add(upperCaseLatin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCharactersInString() throws Exception {
        final String illegalCharacters = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        trie.add(illegalCharacters);
    }
}
