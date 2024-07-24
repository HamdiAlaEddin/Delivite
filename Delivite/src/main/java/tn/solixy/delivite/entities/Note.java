package tn.solixy.delivite.entities;

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
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long NoteID;
    @Enumerated(EnumType.STRING)
    private Rating rate;
    @ManyToOne
    @JoinColumn(name = "client_id")
    public  Client client;
    @ManyToOne
    @JoinColumn(name = "chauffeur_id")
    public Chauffeur chauffeur;
}
