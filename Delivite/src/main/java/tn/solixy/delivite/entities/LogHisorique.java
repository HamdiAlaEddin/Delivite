package tn.solixy.delivite.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"livr", "vehic"})
public class LogHisorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LogID;
    private String description;
    @Enumerated(EnumType.STRING)
    private Incident incident;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "livraison_id")
    private Livraison livr; // Relation avec la livraison
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "Vehicule_id")
    private Vehicule vehic;
}
