package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LivraisonID;
    @Enumerated(EnumType.STRING)
    private StatusLivraison status;
    @Enumerated(EnumType.STRING)
    private TypeLivraison type;
    @Enumerated(EnumType.STRING)
    private TypePayement paiement;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date dateCommande;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date dateLivraison;
    private String adresseLivraison;
    private Long id_client;
    private String position;//GPS
    private BigDecimal prix;
    private String description;
    private Long id_chauffeur;
    private Long id_vehicule;
}
