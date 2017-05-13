package com.kopcheski.jsoncomparision.rest;

import static com.kopcheski.jsoncomparision.Json.jsonObjectResult;
import com.kopcheski.jsoncomparision.service.JsonComparisionService;
import com.kopcheski.jsoncomparision.Sides;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/diff")
public class JsonComparisionEndpoint {
    
    @Inject
    JsonComparisionService service;
    
    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{id}/left")
    public Response left(@PathParam("id") String id, byte[] objectb64) {
        service.add(id, Sides.LEFT, objectb64);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{id}/right")
    public Response right(@PathParam("id") String id, byte[] objectb64) {
        service.add(id, Sides.RIGHT, objectb64);
        return Response.ok().build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getDiff(@PathParam("id") String id) {
        String diff = service.getDiff(id);
        if (diff == null) {
            return Response.noContent().build();
        }
        
        JsonObject result = jsonObjectResult(diff);
        return Response.ok(result).build();
    }
}
