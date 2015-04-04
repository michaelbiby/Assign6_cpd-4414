/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.productlist;
import entities.products;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author c0644696
 */
@MessageDriven(mappedName = "jms/queue")
public class Prodct_listener implements MessageListener {

    @EJB
    productlist productlist;

    @Override
    public void onMessage(Message message) {

        try {
            if (message instanceof TextMessage) {
                String jsonString = ((TextMessage) message).getText();
                JsonObject json = Json.createReader(new StringReader(jsonString)).readObject();
                productlist.add(new products(json));
            }
        } catch (JMSException ex) {
            System.err.println("Failure In JMS");
        } catch (Exception ex) {
            Logger.getLogger(Prodct_listener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
