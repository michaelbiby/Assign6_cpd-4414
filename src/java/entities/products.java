/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author c0644696
 */
public class products {

    private int productId;
    private String name;
    private String description;
    private int quantity;

    public products() {

    }

    public products(int productId, String name, String description, int quantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public products(JsonObject json) {
        this.productId = json.getInt("productId");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.quantity = json.getInt("quantity");
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder().add("productId", productId)
                .add("name", name)
                .add("description", description)
                .add("quantity", quantity).build();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}