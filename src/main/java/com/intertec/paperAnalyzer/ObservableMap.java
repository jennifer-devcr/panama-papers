package com.intertec.paperAnalyzer;

import java.util.*;
import java.util.function.BiFunction;

public class ObservableMap<M extends Map<Officer, Set<Entity>>> implements Map<Officer, Set<Entity>> {
    private M map;
    private List<BiFunction<Officer, Set<Entity>, Boolean>> listenersAddAction;

    public ObservableMap(M map) {
        this.map = map;
        this.listenersAddAction = new ArrayList<>();
    }

    public void registerListenersAddAction(BiFunction<Officer, Set<Entity>, Boolean> func) {
        this.listenersAddAction.add(func);
    }

    public void notifyListenersAddAction(Officer key, Set<Entity> value) {
        this.listenersAddAction.forEach(listener -> listener.apply(key, value));
    }

    public M getMap() {
        return map;
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
    public Set<Entity> get(Object key) {
        return this.map.get(key);
    }

    @Override
    public Set<Entity> put(Officer key, Set<Entity> value) {
        notifyListenersAddAction(key, value);

        return this.map.put(key, value);
    }

    @Override
    public Set<Entity> remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Officer, ? extends Set<Entity>> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<Officer> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<Set<Entity>> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<Officer, Set<Entity>>> entrySet() {
        return this.map.entrySet();
    }
}