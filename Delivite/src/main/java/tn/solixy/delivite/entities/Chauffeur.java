package tn.solixy.delivite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("Chauffeur")
public class Chauffeur extends User{

    private boolean disponible;
    private boolean accepted;
    private String numPermisConduit;
    @OneToMany(mappedBy = "chauffeur", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();
    public void addNote(Note note) {
        notes.add(note);
        note.setChauffeur(this);
    }
    public Chauffeur() {
        super();
        this.setRole(Role.Chauffeur);
    }
    @OneToMany(mappedBy = "chauf", cascade = CascadeType.ALL)
    private List<Livraison> livraisons = new ArrayList<>();
}
