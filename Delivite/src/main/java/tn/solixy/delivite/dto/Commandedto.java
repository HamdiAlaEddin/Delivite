package tn.solixy.delivite.dto;

import tn.solixy.delivite.entities.StatusLivraison;
import tn.solixy.delivite.entities.TypeLivraison;
import tn.solixy.delivite.entities.TypePayement;

import java.math.BigDecimal;
import java.util.Date;

public record Commandedto(

         String restomail,
         String clientmail,
        TypeLivraison typelivraison,
        double price,
       String description,
        TypePayement typepayement


) {
    public String getRestomail() {
        return restomail;
    }

    public String getClientmail() {
        return clientmail;
    }
    public TypeLivraison getTypelivraison() {
        return typelivraison;
    }
    public TypePayement getTypePayement() {
        return typepayement();
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }

}
