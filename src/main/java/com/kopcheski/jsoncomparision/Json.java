/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision;

import static java.util.Base64.getEncoder;
import static javax.json.Json.createObjectBuilder;
import javax.json.JsonObject;

/**
 * Utility class to deal with json objects.
 * 
 * @author kopcheski
 */
public class Json {
    
    public static byte[] jsonObjectAsByteArrayBase64(String valueOfAttrName) {
        final JsonObject jsonObject = createObjectBuilder().add("name", valueOfAttrName).build();
        return getEncoder().encode(jsonObject.toString().getBytes());
    }
    
    public static JsonObject jsonObjectResult(String value) {
        return javax.json.Json.createObjectBuilder().add("result", value).build();
    }
    
}
