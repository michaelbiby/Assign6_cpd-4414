/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.productlist;
import entities.products;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author c0644696
 */
@Path("/products")
@RequestScoped
public class productREST {

    @Inject
    productlist productlist;

    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(productlist.toJSON()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        return Response.ok(productlist.get(id).toJSON()).build();
    }

    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        Response response;
        try {
            productlist.add(new products(json));
            response = Response.ok(productlist.get(json.getInt("productId")).toJSON()).build();
        } catch (Exception ex) {
            response = Response.status(500).build();
        }
        return response;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response set(@PathParam("id") int id, JsonObject json) {
        Response resp;
        try {
            products p = new products(json);
            productlist.set(id, p);
            resp = Response.ok(productlist.get(id).toJSON()).build();
        } catch (Exception ex) {
            resp = Response.status(500).build();
        }
        return resp;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        Response resp;
        try {
            productlist.remove(id);
            resp = Response.ok("Delete the products with its id" + id).build();
        } catch (Exception ex) {
            resp = Response.status(500).build();
        }
        return resp;

    }
}
