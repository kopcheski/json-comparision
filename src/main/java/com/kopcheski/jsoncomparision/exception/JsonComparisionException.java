/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.exception;

/**
 *
 * @author kopcheski
 */
public class JsonComparisionException extends RuntimeException {

    public JsonComparisionException(String message, Exception jpe) {
        super(message, jpe);
    }

    public JsonComparisionException(String message) {
        super(message);
    }
    
}
