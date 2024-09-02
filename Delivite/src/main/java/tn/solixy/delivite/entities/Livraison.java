package tn.solixy.delivite.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate dateCommande;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateLivraison;
    private String adresseLivraison;
    @ManyToOne
    @JoinColumn(name = "id_client")
    @JsonIgnore
    private Client cli;
    private String position;//GPS
    private double prix;
    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_chauffeur")
    private Chauffeur chauf;
    @JsonIgnore
    @ManyToOne
    private Vehicule vehicule;
    @JsonManagedReference
    @OneToMany(mappedBy = "livr")
    private List<LogHisorique> loghs;
}