package com.intertec.paperAnalyzer;


import java.util.Map;
import java.util.Set;

public interface ResultListener {
    public boolean onEntitySetAdded(Officer officer, Set<Entity> entities);
}
