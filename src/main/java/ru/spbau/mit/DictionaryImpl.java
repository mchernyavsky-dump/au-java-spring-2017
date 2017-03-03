package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.abs;

public class DictionaryImpl implements Dictionary {
    private static final int DEFAULT_DICTIONARY_SIZE = 1024;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private String[] keys = new String[DEFAULT_DICTIONARY_SIZE];
    private String[] values = new String[DEFAULT_DICTIONARY_SIZE];
    private int capacity = DEFAULT_DICTIONARY_SIZE;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(@NotNull final String key) {
        final int index = getHashKey(key);
        for (int i = index; keys[index] != null && i < capacity; i++) {
            if (key.equals(keys[index])) {
                return values[index] != null;
            }
        }

        return false;
    }

    @Override
    @Nullable
    public String get(@NotNull final String key) {
        final int index = getHashKey(key);
        for (int i = index; keys[index] != null && i < capacity; i++) {
            if (key.equals(keys[index])) {
                return values[index];
            }
        }

        return null;
    }

    @Override
    @Nullable
    public String put(@NotNull final String key, @NotNull final String value) {
        final int index = getHashKey(key);
        for (int i = index; i < capacity; i++) {
            if (keys[index] == null || key.equals(keys[index])) {
                final String oldValue = values[index];
                keys[index] = key;
                values[index] = value;
                if (oldValue == null) {
                    size++;
                    balanceLoadFactor();
                }

                return oldValue;
            }
        }

        return null;
    }

    @Override
    @Nullable
    public String remove(@NotNull final String key) {
        final int index = getHashKey(key);
        for (int i = index; keys[index] != null && i < capacity; i++) {
            if (key.equals(keys[index])) {
                final String value = values[index];
                if (value != null) {
                    values[index] = null;
                    size--;
                }

                return value;
            }
        }

        return null;
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < capacity; i++) {
            keys[i] = null;
            values[i] = null;
        }
    }

    private void balanceLoadFactor() {
        if ((double) size / capacity < LOAD_FACTOR_THRESHOLD) {
            return;
        }

        final int oldCapacity = capacity;
        capacity *= 2;
        size = 0;

        final String[] oldKeys = keys;
        final String[] oldValues = values;
        keys = new String[capacity];
        values = new String[capacity];
        for (int i = 0; i < oldCapacity; i++) {
            if (oldValues[i] != null) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }

    private int getHashKey(@NotNull final String oldKey) {
        return abs(oldKey.hashCode() % capacity);
    }
}
