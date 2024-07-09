package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime dateCommande;
    private LocalDateTime dateLivraison;
    private String adresseLivraison;
    private String client;////// clientID corriger !!!
    private String position;//GPS
    private BigDecimal prix;
    private String description;
    @ManyToOne
    @JoinColumn(name = "chauffeur_id")
    private User chauffeur; // Chauffeur assigné à la livraison
    @ManyToMany
    @JoinTable(
            name = "livraison_vehicule",
            joinColumns = @JoinColumn(name = "livraison_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicule_id")
    )
    private List<Vehicule> vehicules = new ArrayList<>();
}
