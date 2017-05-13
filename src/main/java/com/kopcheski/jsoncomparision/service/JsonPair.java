/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopcheski.jsoncomparision.Sides;
import com.kopcheski.jsoncomparision.exception.JsonComparisionException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Represents a pair (left and right based) of json in a byte array form.
 *  
 * @author kopcheski
 */
public class JsonPair {
    
    public static final String INVALID_JSON_OBJECT = "Invalid JSON Object: ";

    private byte[] objectB64Left;
    private byte[] objectB64Right;

    public JsonPair() {
    }

    /**
     * 
     * 
     * @param side
     * @param objectB64 
     * @throws JsonComparisionException byte arrays does not contain json in base64.
     */
    public void add(Sides side, byte[] objectB64) {
        validate(objectB64);
        if (Sides.LEFT.equals(side)) {
            this.objectB64Left = objectB64;
        } else {
            this.objectB64Right = objectB64;
        }
    }

    private void validate(byte[] objectB64) {
        final byte[] decoded = Base64.getDecoder().decode(objectB64);
        String json = new String(decoded);
        try {
            final JsonParser parser = new ObjectMapper().getFactory()
                    .createParser(json);
            while (parser.nextToken() != null) {}
        } catch (JsonParseException jpe) {
            throw new JsonComparisionException(INVALID_JSON_OBJECT + json, jpe);
        } catch (IOException ioe) {
            throw new JsonComparisionException(INVALID_JSON_OBJECT + json, ioe);
        }
    }

    public byte[] getObjectB64Right() {
        return objectB64Right;
    }

    public byte[] getObjectB64Left() {
        return objectB64Left;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof JsonPair)) {
            return false;
        }

        JsonPair other = (JsonPair) obj;
        return Arrays.equals(this.objectB64Right, other.getObjectB64Right())
                && Arrays.equals(this.objectB64Left, other.getObjectB64Left());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Arrays.hashCode(this.objectB64Left);
        hash = 43 * hash + Arrays.hashCode(this.objectB64Right);
        return hash;
    }

}
