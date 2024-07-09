package tn.solixy.delivite.services;

import tn.solixy.delivite.entities.*;

import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> getAll() ;
     Map<String, Object> convertToMap(User us);
    public List<User> findByRole(RoleName roleName);

}
