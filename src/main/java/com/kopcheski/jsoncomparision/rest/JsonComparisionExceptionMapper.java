/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision.rest;

import com.kopcheski.jsoncomparision.exception.JsonComparisionException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author kopcheski
 */
@Provider
public class JsonComparisionExceptionMapper implements ExceptionMapper<JsonComparisionException> {

    @Override
    public Response toResponse(JsonComparisionException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
    
}
