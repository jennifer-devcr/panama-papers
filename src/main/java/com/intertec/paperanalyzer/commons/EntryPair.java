package com.intertec.paperanalyzer.commons;


public class EntryPair<E, V> {
    private E entry;
    private V value;

    public EntryPair(E entry, V value) {
        this.entry = entry;
        this.value = value;
    }

    public E getEntry() {
        return entry;
    }

    public void setEntry(E entry) {
        this.entry = entry;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
