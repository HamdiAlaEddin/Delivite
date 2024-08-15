package tn.solixy.delivite.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogHisorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LogID;
    private String description;
    @Enumerated(EnumType.STRING)
    private Incident incident;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "livraison_id")
    private Livraison livr; // Relation avec la livraison
    @ManyToOne
    @JoinColumn(name = "Vehicule_id")
    private Vehicule vehic;
}
