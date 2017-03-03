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
        for (int i = getHashKey(key); keys[i] != null && i < capacity; i++) {
            if (key.equals(keys[i])) {
                return values[i] != null;
            }
        }

        return false;
    }

    @Override
    @Nullable
    public String get(@NotNull final String key) {
        for (int i = getHashKey(key); keys[i] != null && i < capacity; i++) {
            if (key.equals(keys[i])) {
                return values[i];
            }
        }

        return null;
    }

    @Override
    @Nullable
    public String put(@NotNull final String key, @NotNull final String value) {
        for (int i = getHashKey(key); i < capacity; i++) {
            if (keys[i] == null || key.equals(keys[i])) {
                final String oldValue = values[i];
                keys[i] = key;
                values[i] = value;
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
        for (int i = getHashKey(key); keys[i] != null && i < capacity; i++) {
            if (key.equals(keys[i])) {
                final String value = values[i];
                if (value != null) {
                    values[i] = null;
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
