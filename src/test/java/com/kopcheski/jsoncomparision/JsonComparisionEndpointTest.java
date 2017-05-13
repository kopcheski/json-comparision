/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kopcheski.jsoncomparision;

import static com.kopcheski.jsoncomparision.service.ByteArrayComparision.DIFFERENT_SIZE;
import static com.kopcheski.jsoncomparision.service.ByteArrayComparision.EQUALS;
import static com.kopcheski.jsoncomparision.service.JsonPair.INVALID_JSON_OBJECT;
import java.net.URISyntaxException;
import java.net.URL;
import static java.util.Base64.getEncoder;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.arquillian.DefaultDeployment;
import static com.kopcheski.jsoncomparision.Json.jsonObjectAsByteArrayBase64;
import javax.json.JsonObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author kopcheski
 */
@DefaultDeployment(testable = false)
@RunWith(Arquillian.class)
public class JsonComparisionEndpointTest {

    @ArquillianResource
    private URL deploymentURL;


    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        return new Swarm();
    }

    @Test
    public void testAcceptsJson() throws URISyntaxException {
        byte[] objectb64 = jsonObjectAsByteArrayBase64("ASD");
        
        Response result = post("id_json", "left", objectb64);

        assertEquals(OK.getStatusCode(), result.getStatus());
    }

    @Test
    public void testRejectsNonJson() throws URISyntaxException {
        byte[] objectb64 = getEncoder().encode("Non Json Value".getBytes());
        
        Response result = post("id_non_json", "left", objectb64);

        String comparisionResult = result.readEntity(String.class);
        assertEquals(BAD_REQUEST.getStatusCode(), result.getStatus());
        assertTrue(comparisionResult.contains(INVALID_JSON_OBJECT));
    }
    
    @Test
    public void testMessageForEquality() throws URISyntaxException {
        byte[] objectb64 = jsonObjectAsByteArrayBase64("ASD");
        
        String id = "id_equality";
        post(id, "left", objectb64);
        post(id, "right", objectb64);
        
        Response result = get(id);

        String comparisionResult = result.readEntity(String.class);
        JsonObject expected = Json.jsonObjectResult(EQUALS);
        
        assertEquals(OK.getStatusCode(), result.getStatus());
        assertEquals(expected.toString(), comparisionResult);
    }
    
    @Test
    public void testMessageForDiffContent() throws URISyntaxException {
        byte[] objectb64_1 = jsonObjectAsByteArrayBase64("ASD");
        byte[] objectb64_2 = jsonObjectAsByteArrayBase64("XYZ");
        
        String id = "id_diffcontent";
        
        post(id, "left", objectb64_1);
        post(id, "right", objectb64_2);
        
        Response result = get(id);

        String comparisionResult = result.readEntity(String.class);
        
        assertEquals(OK.getStatusCode(), result.getStatus());
        assertTrue(comparisionResult.contains("The first diff"));
    }
    
    @Test
    public void testMessageForDiffSize() throws URISyntaxException {
        byte[] objectb64_1 = jsonObjectAsByteArrayBase64("ASD");
        byte[] objectb64_2 = jsonObjectAsByteArrayBase64("DIFFERENTSIZE");
        
        String id = "id_diffsize";
        
        post(id, "left", objectb64_1);
        post(id, "right", objectb64_2);
        
        Response result = get(id);

        String comparisionResult = result.readEntity(String.class);
        JsonObject expected = Json.jsonObjectResult(DIFFERENT_SIZE);
        
        assertEquals(OK.getStatusCode(), result.getStatus());
        assertEquals(expected.toString(), comparisionResult);
    }
    
    @Test
    public void testMessageForNoContent() throws URISyntaxException {
        Response result = get("new id");

        assertEquals(NO_CONTENT.getStatusCode(), result.getStatus());
    }
    
    private Response post(String id, String side, byte[] objectb64) throws URISyntaxException {
        final Entity<byte[]> entity = Entity.entity(objectb64, MediaType.APPLICATION_OCTET_STREAM);
        
        return ClientBuilder.newClient()
                .target(deploymentURL.toURI() + "/v1/diff/" + id + "/" + side)
                .request(MediaType.APPLICATION_OCTET_STREAM)
                .post(entity, Response.class);
    }
    
    private Response get(String id) throws URISyntaxException {
        return ClientBuilder.newClient()
                .target(deploymentURL.toURI() + "/v1/diff/" + id) 
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
    }
}
