package tn.solixy.delivite.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
public class LivraisonDto {
private Long livraisonID;
private String status;
private String type;
private String paiement;
private LocalDate dateCommande;
private LocalDate dateLivraison;
private String adresseLivraison;
private String clientFirstName;
private String clientPhone;
private String position;
private Double prix;
private String description;
private String chaufFirstName;
private String chaufPhone;
private String vehiculeDetails;

// Constructeurs
public LivraisonDto(Long livraisonID, String name, String named, String paiement, Date dateCommande, Date dateLivraison, String adresseLivraison, String firstName, String phoneNumber, String position, double prix, String description, String chaufFirstName, String number, String immatriculation) {}

public LivraisonDto(Long livraisonID, String status, String type, String paiement,
                    LocalDate dateCommande, LocalDate dateLivraison, String adresseLivraison,
                    String clientFirstName, String clientPhone, String position,
                    double prix, String description, String chaufFirstName,
                    String chaufPhone, String vehiculeDetails) {
    this.livraisonID = livraisonID;
    this.status = status;
    this.type = type;
    this.paiement = paiement;
    this.dateCommande = dateCommande;
    this.dateLivraison = dateLivraison;
    this.adresseLivraison = adresseLivraison;
    this.clientFirstName = clientFirstName;
    this.clientPhone = clientPhone;
    this.position = position;
    this.prix = prix;
    this.description = description;
    this.chaufFirstName = chaufFirstName;
    this.chaufPhone = chaufPhone;
    this.vehiculeDetails = vehiculeDetails;
}

// Getters et setters
public Long getLivraisonID() { return livraisonID; }
public void setLivraisonID(Long livraisonID) { this.livraisonID = livraisonID; }

public String getStatus() { return status; }
public void setStatus(String status) { this.status = status; }

public String getType() { return type; }
public void setType(String type) { this.type = type; }

public String getPaiement() { return paiement; }
public void setPaiement(String paiement) { this.paiement = paiement; }

public LocalDate getDateCommande() { return dateCommande; }
public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }

public LocalDate getDateLivraison() { return dateLivraison; }
public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }

public String getAdresseLivraison() { return adresseLivraison; }
public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

public String getClientFirstName() { return clientFirstName; }
public void setClientFirstName(String clientFirstName) { this.clientFirstName = clientFirstName; }

public String getClientPhone() { return clientPhone; }
public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

public String getPosition() { return position; }
public void setPosition(String position) { this.position = position; }

public Double getPrix() { return prix; }
public void setPrix(Double prix) { this.prix = prix; }

public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }

public String getChaufFirstName() { return chaufFirstName; }
public void setChaufFirstName(String chaufFirstName) { this.chaufFirstName = chaufFirstName; }

public String getChaufPhone() { return chaufPhone; }
public void setChaufPhone(String chaufPhone) { this.chaufPhone = chaufPhone; }

public String getVehiculeDetails() { return vehiculeDetails; }
public void setVehiculeDetails(String vehiculeDetails) { this.vehiculeDetails = vehiculeDetails; }
}
