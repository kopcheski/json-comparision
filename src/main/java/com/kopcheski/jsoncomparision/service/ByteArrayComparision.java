/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.service;

import java.util.Arrays;


/**
 * Compares two byte arrays looking for differences.
 * 
 * @author kopcheski
 */
public class ByteArrayComparision {
    
    public static final String EQUALS = "equals";
    public static final String DIFFERENT_SIZE = "different size";
    
    /**
     * 
     * @param left
     * @param right
     * @return 'equals', 'different size' or a message informing the differente offset and size. 
     */
    public String compare(byte[] left, byte[] right) {
        if (Arrays.equals(left, right)) {
            return EQUALS;
        } else if (left.length != right.length){
            return DIFFERENT_SIZE;
        } else {
            return getOffsetAndLength(left, right);
        }
    }

    private String getOffsetAndLength(byte[] left, byte[] right) {
        long offset = 0;
        long diffSize;
        int length = left.length;
        boolean hasDiff = false;
        int i = 0;
        for (; i < length; i++) { // O(n)
            boolean actualEquals = left[i] == right[i];
            if (!actualEquals) {
                offset = offset > 0 ? offset : i;
                hasDiff = true;
            } 
            if(hasDiff && actualEquals) {
                break;
            }
        }
        diffSize = i - offset;
        
        return String.format("The first diff has an offset at %d and a size of %d.", offset, diffSize);
    }
    
}
