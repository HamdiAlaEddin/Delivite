package tn.solixy.delivite.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.solixy.delivite.entities.Livraison;
import tn.solixy.delivite.entities.Role;
import tn.solixy.delivite.entities.User;
import tn.solixy.delivite.entities.Vehicule;
import tn.solixy.delivite.repositories.ILivraisonRepository;
import tn.solixy.delivite.repositories.IUserRepository;
import tn.solixy.delivite.repositories.IVehiculeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GestionDelivite implements IGestionDelivite {


    @Autowired
    ILivraisonRepository iLivraisonRepository;
    IUserRepository iUserRepository;
    IVehiculeRepository iVehiculeRepository;
    @Override
    public List<User> retrieveAllUsers() {
        return iUserRepository.findAll();
    }
    @Override
    public List<Livraison> retrieveAllLivraisons() {
        return iLivraisonRepository.findAll();
    }

    @Override
    public List<Vehicule> retrieveAllVehicule() {
        return iVehiculeRepository.findAll();
    }

    @Override
    public List<User> retrieveAllUsersByRole(Role r) {
        List<User> filteredUsers = new ArrayList<>();
        List<User> allUsers = iUserRepository.findAll();
        for(User user:allUsers){
            if(user.getRole()==r){
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }
    @Override
    public User updateUser(User u) {
        return iUserRepository.save(u);
    }

    @Override
    public Livraison updateLivraison(Livraison l) {
        return iLivraisonRepository.save(l);
    }

    @Override
    public Vehicule updateVehicule(Vehicule v) {
        return iVehiculeRepository.save(v);
    }
    @Override
    public void DeleteUser(Long Uid) {
        iUserRepository.deleteById(Uid);
    }

    @Override
    public void DeleteLivraison(Long Lid) {
        iLivraisonRepository.deleteById(Lid);
    }
    @Override
    public void DeleteVehicule(Long Vid) {
        iVehiculeRepository.deleteById(Vid);
    }
    @Override
    public User addUser(User user) {
        return null;
    }

    public User addChauffeurAndAssignToVehicule(User chauffeur, Vehicule vehicule) {
        chauffeur.setVehicule(vehicule);
        return iUserRepository.save(chauffeur);
    }
    public Livraison addLivraisonAndAssignToLivreur(Livraison livraison, User chauffeur) {
        livraison.setChauffeur(chauffeur);
        return iLivraisonRepository.save(livraison);
    }

}
