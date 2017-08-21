package com.intertec.paperanalyzer.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ObservableMap<K, V> implements Map<K, V> {
    private Map<K, V> map;
    private List<BiConsumer<K, V>> listenersAddAction;

    public ObservableMap(Map<K, V> map) {
        this.map = map;
        this.listenersAddAction = new ArrayList<>();
    }

    public void registerListenersAddAction(BiConsumer<K, V> func) {
        this.listenersAddAction.add(func);
    }

    public void notifyListenersAddAction(K key, V value) {
        this.listenersAddAction.forEach(listener -> listener.accept(key, value));
    }


    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.map.get(key);
    }

    @Override
    public V put(K key, V value) {
        notifyListenersAddAction(key, value);
        return this.map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }
}