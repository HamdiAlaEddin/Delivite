package tn.solixy.delivite.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.solixy.delivite.entities.Livraison;
import tn.solixy.delivite.entities.Role;
import tn.solixy.delivite.entities.User;
import tn.solixy.delivite.entities.Vehicule;
import tn.solixy.delivite.services.IGestionDelivite;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Delivite")
//@CrossOrigin("*")
public class DeliviteController {
    @Autowired
    IGestionDelivite service;
    @GetMapping("/getallUsers")
    public List<User> getAll(){
        return service.retrieveAllUsers();
    }
    @GetMapping("/getUsers/{role}")
    public List<User> getAllUserByRole(@PathVariable("role") Role role){
        return service.retrieveAllUsersByRole(role);
    }
    @GetMapping("/getallLivraison")
    public List<Livraison> getAllLivraison(){
        return service.retrieveAllLivraisons();
    }
    @GetMapping("/getallVehicules")
    public List<Vehicule> getAllV(){
        return service.retrieveAllVehicule();
    }
    @PutMapping("/updateUsers")
    public User updateUsers(@RequestBody User user){
        return  service.updateUser(user);
    }
    @PutMapping("/updateLivraison")
    public Livraison updateLivraison(@RequestBody Livraison livraison){
        return  service.updateLivraison(livraison);
    }
    @PutMapping("/updateVehicule")
    public Vehicule updateVehicule(@RequestBody Vehicule vehicule){
        return  service.updateVehicule(vehicule);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteU(@PathVariable("id") Long Uid){
        service.DeleteUser(Uid);
    }
    @DeleteMapping("/deleteVehicule/{id}")
    public void deleteV(@PathVariable("id") Long VID){
        service.DeleteVehicule(VID);
    }
    @DeleteMapping("/deleteLivraison/{id}")
    public void deleteL(@PathVariable("id") Long LID){
        service.DeleteLivraison(LID);
    }
}
