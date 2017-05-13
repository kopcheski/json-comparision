/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.service;

import com.kopcheski.jsoncomparision.Sides;
import com.kopcheski.jsoncomparision.exception.JsonComparisionException;
import com.kopcheski.jsoncomparision.repo.Repository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Assert;
import static com.kopcheski.jsoncomparision.service.JsonComparisionService.ALL_PARAMETERS_MUST_BE_NOT_NULL;
import static org.junit.Assert.assertNull;

/**
 *
 * @author kopcheski
 */
public class JsonComparisionServiceTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testNullArgumentObject() {
        configureExpectedException();
        new JsonComparisionService().add("", Sides.RIGHT, null);
    }

    @Test
    public void testNullArgumentSide() {
        configureExpectedException();
        new JsonComparisionService().add("", null, new byte[]{});
    }

    @Test
    public void testNullArgumentId() {
        configureExpectedException();
        new JsonComparisionService().add(null, Sides.RIGHT, new byte[]{});
    }

    @Test
    public void testNonNullArguments() {
        try {
            new JsonComparisionService().add("", Sides.RIGHT, new byte[]{});
        } catch (IllegalArgumentException iae) {
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void testComparisionWithUnexistentValue() {
        assertNull(new JsonComparisionService(new Repository()).getDiff("unexistent"));
    }

    private void configureExpectedException() {
        expectedEx.expect(JsonComparisionException.class);
        expectedEx.expectMessage(ALL_PARAMETERS_MUST_BE_NOT_NULL);
    }

}
