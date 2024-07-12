package tn.solixy.delivite.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.solixy.delivite.entities.*;
import tn.solixy.delivite.services.IGestionDelivite;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/Delivite")
//@CrossOrigin("*")
public class DeliviteController {
    IGestionDelivite service;
  @GetMapping("/role/{roleName}")
    public List<User> getUsersByRole(@PathVariable Role roleName) {
        return service.findByRole(roleName);
    }
    /*  @GetMapping("/allUsers")
    public List<Map<String, Object>> getAllUsers() {
        return service.getAll();
    }*/
    @PostMapping("/addUser/{Role}")
    public User addUser(@PathVariable("Role") Role Role,@RequestBody User user) {
        return service.addUser(user);
    }
    @PostMapping("/addVehicule")
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return service.addVehicule(vehicule);
    }
    @PostMapping("/addLivraison")
    public Livraison addLivraison(@RequestBody Livraison livraison) {
        return service.addLivraison(livraison);
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
