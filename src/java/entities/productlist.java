/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author c0644696
 */
@ApplicationScoped
public class productlist {

    private List<products> productlist;

    public productlist() {
        productlist = new ArrayList<>();
        try (Connection con = getConnection()) {
            String query = "SELECT * FROM PRODUCT";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products p = new products(rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"));
                productlist.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(productlist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JsonArray toJSON() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (products p : productlist) {
            json.add(p.toJSON());
        }
        return json.build();
    }

    public products get(int productId) {
        products res = null;
        for (products p : productlist) {
            if (p.getProductId() == productId) {
                res = p;
            }
        }
        return res;
    }

    public void set(int productId, products p) {
        int res = doUpdate(
                "UPDATE PRODUCT SET name = ?, description = ?, quantity = ? where productId = ?",
                p.getName(),
                p.getDescription(),
                String.valueOf(p.getQuantity()),
                String.valueOf(productId));
        if (res > 0) {
            products original = get(productId);
            original.setName(p.getName());
            original.setDescription(p.getDescription());
            original.setQuantity(p.getQuantity());
        }

    }

    public void add(products p) throws Exception {
        int res = doUpdate(
                "INSERT INTO PRODUCT (productId, name, description, quantity) values (?, ?, ?, ?)",
                String.valueOf(p.getProductId()),
                p.getName(),
                p.getDescription(),
                String.valueOf(p.getQuantity()));
        if (res > 0) {
            productlist.add(p);
        } else {
            throw new Exception("Error");
        }
    }

    public void remove(products p) throws Exception {
        remove(p.getProductId());
    }

    public void remove(int productId) throws Exception {
        int res = doUpdate("DELETE FROM PRODUCT where productId = ?",
                String.valueOf(productId));
        if (res > 0) {
            products original = get(productId);
            productlist.remove(original);
        } else {
            throw new Exception("Failure In Deletion");
        }

    }

    private Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://localhost/assign6";
            con = (Connection) DriverManager.getConnection(jdbc, "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(productlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    private int doUpdate(String query, String... params) {
        int number_change = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            number_change = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(productlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        return number_change;
    }

}
