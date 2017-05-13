/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.service;

import com.kopcheski.jsoncomparision.Sides;
import com.kopcheski.jsoncomparision.exception.JsonComparisionException;
import com.kopcheski.jsoncomparision.repo.Repository;
import java.util.Objects;
import javax.inject.Inject;

/**
 * Service responsible for all the functionalities. 
 * 
 * @author kopcheski
 */
public class JsonComparisionService {
    
    static final String ALL_PARAMETERS_MUST_BE_NOT_NULL = "All parameters must be not null.";
    
    @Inject
    Repository repo;
    
    public JsonComparisionService() {
        
    }
    
    public JsonComparisionService(Repository repo) {
        this.repo = repo;
    }
    
    public void add(String id, Sides sides, byte[] objectb64) {
        checkNotNull(id, sides, objectb64);
        repo.add(id, sides, objectb64);
    }

    public String getDiff(String id) {
        checkNotNull(id);
        JsonPair sideValue = repo.get(id);
        if (sideValue == null) {
            return null;
        }
        return new ByteArrayComparision()
                .compare(sideValue.getObjectB64Left(), sideValue.getObjectB64Right());
    }
    
    private void checkNotNull(Object... nullables) {
        for (Object object : nullables) {
            if (Objects.isNull(object)) {
                throw new JsonComparisionException(ALL_PARAMETERS_MUST_BE_NOT_NULL);
            }
        }
    }

}
