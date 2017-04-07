package ru.spbau.mit;

import java.util.*;

public class HashMultiset<E> extends AbstractSet<E> implements Multiset<E> {
    private final Map<E, Integer> storage = new LinkedHashMap<>();
    private int size = 0;

    @Override
    public int count(final Object element) {
        return storage.getOrDefault(element, 0);
    }

    @Override
    public Set<E> elementSet() {
        return storage.keySet();
    }

    @Override
    public Set<Entry<E>> entrySet() {
        return new AbstractSet<Entry<E>>() {
            @Override
            public int size() {
                return storage.size();
            }

            @Override
            public Iterator<Entry<E>> iterator() {
                return new Iterator<Entry<E>>() {
                    private final Iterator<Map.Entry<E, Integer>> wrappedIterator = storage.entrySet().iterator();
                    private int count = 0;

                    @Override
                    public boolean hasNext() {
                        return wrappedIterator.hasNext();
                    }

                    @Override
                    public Entry<E> next() {
                        final Entry<E> entry = new Entry<E>() {
                            private final Map.Entry<E, Integer> wrappedEntry = wrappedIterator.next();

                            @Override
                            public E getElement() {
                                return wrappedEntry.getKey();
                            }

                            @Override
                            public int getCount() {
                                return wrappedEntry.getValue();
                            }
                        };

                        count = entry.getCount();
                        return entry;
                    }

                    @Override
                    public void remove() {
                        wrappedIterator.remove();
                        size -= count;
                    }
                };
            }
        };
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<Map.Entry<E, Integer>> wrappedIterator = storage.entrySet().iterator();
            private Map.Entry<E, Integer> currentEntry = null;
            private int remainCount = 0;
            private boolean removed = false;

            @Override
            public boolean hasNext() {
                return remainCount > 0 || wrappedIterator.hasNext();
            }

            @Override
            public E next() {
                if (remainCount == 0) {
                    currentEntry = wrappedIterator.next();
                    remainCount = currentEntry.getValue();
                }

                --remainCount;
                removed = false;
                return currentEntry.getKey();
            }

            @Override
            public void remove() {
                if (removed || currentEntry == null) {
                    throw new IllegalStateException();
                }

                final Integer count = currentEntry.getValue();
                if (count > 1) {
                    currentEntry.setValue(count - 1);
                } else {
                    wrappedIterator.remove();
                }

                --size;
                removed = true;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(final Object object) {
        return storage.keySet().contains(object);
    }

    @Override
    public boolean add(final E element) {
        storage.put(element, count(element) + 1);
        ++size;
        return true;
    }

    @Override
    public boolean remove(final Object object) {
        final int count = count(object);
        if (count > 0) {
            if (count > 1) {
                storage.put((E) object, count - 1);
            } else {
                storage.remove(object);
            }

            --size;
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }
}
