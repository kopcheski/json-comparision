/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.service;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author kopcheski
 */
@RunWith(Parameterized.class)
public class ByteArrayComparisionTest {
    
    @Parameterized.Parameters
    public static Collection dataSet() {
        return Arrays.asList(new Object[][]{
            {bytearray(1,2,3), bytearray(1,2,3), "equals"},
            {bytearray(1,2,3), bytearray(1,2,3,4), "different size"},
            {bytearray(1,2,3), bytearray(1,2,4), message(2, 1)},
            {bytearray(1,2,3), bytearray(1,3,4), message(1, 2)},
            {bytearray(3,2,1), bytearray(1,2,3), message(0, 1)}
            
        });
    }
    
    private final byte[] left, right; 
    private final String expected;
    
    public ByteArrayComparisionTest(byte[] left, byte[] right, String expected) {
        this.left = left;
        this.right = right;
        this.expected = expected;
    }

    @Test
    public void testComparision() {
        String actual = new ByteArrayComparision().compare(left, right);
        assertEquals(expected, actual);
    }

    private static byte[] bytearray(int... values) {
        byte[] array = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = (byte) values[i];
        }
        return array;
    }
    
    private static String message(int offset, int size) {
        return String.format("The first diff has an offset at %d and a size of %d.", offset, size);
    }

}