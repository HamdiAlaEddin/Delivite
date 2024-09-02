package tn.solixy.delivite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("CHAUFFEUR")
public class Chauffeur extends User{
    @Column(name = "disponible", nullable = true)
    private boolean disponible = true;
    @Column(name = "accepted", nullable = true)
    private boolean accepted = false;
    @Column(name = "num_permis_conduit", nullable = true)
    private String numPermisConduit;
    @OneToMany(mappedBy = "chauffeur", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();
    public void addNote(Note note) {
        notes.add(note);
        note.setChauffeur(this);
    }
    public Chauffeur() {
        super();
        this.setRole(Role.CHAUFFEUR);
        this.accepted = false;
    }

    @OneToMany(mappedBy = "chauf", cascade = CascadeType.ALL)
    private List<Livraison> livraisons = new ArrayList<>();
    public boolean isAccepted() {
        return accepted;
    }
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

}
