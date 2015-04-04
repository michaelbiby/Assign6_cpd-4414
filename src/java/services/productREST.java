/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entities.productlist;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 *
 * @author c0644696
 */
@Path("/products")
@RequestScoped
public class productREST {
    @Inject
    productlist productlist;
}
