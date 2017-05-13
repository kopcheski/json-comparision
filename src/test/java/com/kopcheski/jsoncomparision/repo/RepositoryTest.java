/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.repo;

import com.kopcheski.jsoncomparision.service.JsonPair;
import com.kopcheski.jsoncomparision.Sides;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import static com.kopcheski.jsoncomparision.Json.jsonObjectAsByteArrayBase64;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 *
 * @author kopcheski
 */
public class RepositoryTest {
    
    static Repository jsonRepository = new Repository();
    
    @Test
    public void addJsonB64() {
        byte[] objectB64 = jsonObjectAsByteArrayBase64("a");
        
        jsonRepository.add("id", Sides.LEFT, objectB64);
        
        final JsonPair expectedSideValue = new JsonPair();
        expectedSideValue.add(Sides.LEFT, objectB64);
        
        JsonPair actual = jsonRepository.get("id");
        Assert.assertEquals(expectedSideValue, actual);
        Assert.assertNotEquals(expectedSideValue, new JsonPair());
    }
    
    @Test
    public void addTwoValuesAtTheSameSideWithTheSameId() {
        byte[] objectB64_1 = jsonObjectAsByteArrayBase64("a");
        byte[] objectB64_2 = jsonObjectAsByteArrayBase64("b");
        
        jsonRepository.add("id", Sides.LEFT, objectB64_1);
        jsonRepository.add("id", Sides.LEFT, objectB64_2);
        
        final JsonPair expectedSideValue = new JsonPair();
        expectedSideValue.add(Sides.LEFT, objectB64_2);
        
        JsonPair actual = jsonRepository.get("id");
        assertEquals("Second one takes place", expectedSideValue, actual);
        assertNotEquals(expectedSideValue, new JsonPair());
    }
    
}
