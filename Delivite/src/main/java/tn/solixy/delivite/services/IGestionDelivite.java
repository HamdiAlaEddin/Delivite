package tn.solixy.delivite.services;

import tn.solixy.delivite.entities.Livraison;
import tn.solixy.delivite.entities.Role;
import tn.solixy.delivite.entities.User;
import tn.solixy.delivite.entities.Vehicule;

import java.util.List;

public interface IGestionDelivite {
    User addUser(User user);
    List<User> retrieveAllUsers();
    List<Livraison> retrieveAllLivraisons();
    List<Vehicule> retrieveAllVehicule();

    List<User> retrieveAllUsersByRole(Role role);
    User updateUser(User u);
    Livraison updateLivraison(Livraison l);
    Vehicule updateVehicule(Vehicule v);
    void DeleteUser(Long Uid);
    void DeleteLivraison(Long Lid);
    void DeleteVehicule(Long Vid);
    public Livraison addLivraisonAndAssignToLivreur(Livraison livraison, User chauffeur);
    public User addChauffeurAndAssignToVehicule(User chauffeur, Vehicule vehicule);


}
