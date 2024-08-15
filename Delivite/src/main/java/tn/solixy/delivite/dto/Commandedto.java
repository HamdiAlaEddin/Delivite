package tn.solixy.delivite.dto;

import tn.solixy.delivite.entities.StatusLivraison;
import tn.solixy.delivite.entities.TypeLivraison;
import tn.solixy.delivite.entities.TypePayement;

import java.math.BigDecimal;
import java.util.Date;

public record Commandedto(

   Long livraisonID,
        StatusLivraison status,
   TypeLivraison type,
     TypePayement paiement,
     Date dateCommande,
     Date dateLivraison,
     String adresseLivraison,
  String clientFirstName,
    String clientLastName,
   String position,
    BigDecimal prix,
     String description,
     String chauffeurFirstName,
    String chauffeurLastName,
     String vehiculeImmat



) {
}
