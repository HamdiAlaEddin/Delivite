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
        BigDecimal price,
       String description



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
    public BigDecimal getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }

}
