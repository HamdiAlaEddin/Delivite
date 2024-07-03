package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private LocalDateTime dateCommande;
    private LocalDateTime dateLivraison;

    private String adresseLivraison;
    private String client;////// clientID corriger !!!
    private String position;

    private BigDecimal prix;
    private String description;
    @ManyToOne
    @JoinColumn(name = "vehicule_id") // Nom de la colonne de clé étrangère dans la table Livraison
    private Vehicule vehicule;
}
