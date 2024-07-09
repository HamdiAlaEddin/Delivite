package tn.solixy.delivite.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.solixy.delivite.entities.*;
import tn.solixy.delivite.repositories.ILivraisonRepository;
import tn.solixy.delivite.repositories.IRoleRepository;
import tn.solixy.delivite.repositories.IUserRepository;
import tn.solixy.delivite.repositories.IVehiculeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GestionDelivite implements IGestionDelivite {

    ILivraisonRepository iLivraisonRepository;
    IUserRepository iUserRepository;
    IVehiculeRepository iVehiculeRepository;
    IRoleRepository iRoleRepository;

    @Override
     public List<Map<String, Object>> getAll() {
            List<User> users = iUserRepository.findAll();
            return users.stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
        }
    @Override
        public Map<String, Object> convertToMap(User us) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", us.getUserID());
            userMap.put("email", us.getEmail());
            userMap.put("firstName", us.getFirstName());
            userMap.put("lastName", us.getLastName());
            userMap.put("role", us.getRole());


            if (us.getRole().getRole() == RoleName.Chauffeur) {
                userMap.put("drivingLicenseNumber", us.getNumPermisConduit());
                userMap.put("rate", us.getRate());
                userMap.put("vehicle", us.getVehicule());
            } else {
                userMap.put("drivingLicenseNumber", "");
                userMap.put("rate", 0);
                userMap.put("vehicle", null);
            }

            return userMap;
        }

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
        // Assurez-vous que le rôle existe
        Role role = iRoleRepository.findByRole(user.getRole().getRole());
        if (role == null) {
            throw new IllegalArgumentException("Invalid role: " + user.getRole().getRole());
        }
        user.setRole(role);
        return iUserRepository.save(user);
    }


    public User addChauffeurAndAssignToVehicule(User chauffeur, Vehicule vehicule) {
        chauffeur.setVehicule(vehicule);
        return iUserRepository.save(chauffeur);
    }
    public Livraison addLivraisonAndAssignToLivreur(Livraison livraison, User chauffeur) {
        livraison.setChauffeur(chauffeur);
        return iLivraisonRepository.save(livraison);
    }
    @Override
    public List<User> findByRole(RoleName roleName) {
        Role r = iRoleRepository.findByRole(roleName);
        return iUserRepository.findByRole(r);
    }

}
