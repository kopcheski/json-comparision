package com.kopcheski.jsoncomparision.repo;

import com.kopcheski.jsoncomparision.service.JsonPair;
import com.kopcheski.jsoncomparision.Sides;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

/**
 * Stores all json representations in base64.
 * 
 * @author kopcheski
 */
@Singleton
public class Repository {
    
    private final Map<String, JsonPair> repo = new HashMap<>();
    
    public JsonPair get(String id) {
        JsonPair sideValue = repo.get(id);
        return sideValue;
    }

    public void add(String id, Sides sides, byte[] objectb64) {
        JsonPair sideValueRepo = repo.get(id);
        JsonPair actual;
        if (sideValueRepo == null) {
            actual = new JsonPair();
        } else {
            actual = sideValueRepo;
        }
        
        actual.add(sides, objectb64);
        repo.put(id, actual);
    }
    
}
