/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.service;

import com.kopcheski.jsoncomparision.Sides;
import java.util.Base64;
import org.junit.Test;
import static com.kopcheski.jsoncomparision.Json.jsonObjectAsByteArrayBase64;

/**
 *
 * @author kopcheski
 */
public class JsonPairTest {
    
    @Test
    public void testValidJson() {
        byte[] objectB64 = jsonObjectAsByteArrayBase64("ASD");
        new JsonPair().add(Sides.LEFT, objectB64);
    }
    
    @Test(expected = RuntimeException.class)
    public void testInvalidJson() {
        byte[] objectB64 = Base64.getEncoder().encode("ASD".getBytes());
        new JsonPair().add(Sides.LEFT, objectB64);
    }
    
}
