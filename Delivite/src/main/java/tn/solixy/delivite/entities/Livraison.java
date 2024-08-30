package tn.solixy.delivite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCommande;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateLivraison;
    private String adresseLivraison;
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client cli;
    private String position;//GPS
    private BigDecimal prix;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_chauffeur")
    private Chauffeur chauf;
    @ManyToOne
    private Vehicule vehicule;
    @OneToMany(mappedBy = "livr")
    @JsonIgnore
    private List<LogHisorique> loghs;
}