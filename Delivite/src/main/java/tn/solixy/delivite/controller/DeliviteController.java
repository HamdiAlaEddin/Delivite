package tn.solixy.delivite.controller;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.solixy.delivite.entities.*;
import tn.solixy.delivite.services.IGestionDelivite;

import java.math.BigDecimal;
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
    @PostMapping("/addLogLivraison")
    public LogHisorique addLogLivraison(@RequestBody LogHisorique logHisorique) {
        return service.addLog(logHisorique);
    }
    @GetMapping("/getallLivraison")
    public List<Livraison> getAllLivraison(){
        return service.retrieveAllLivraisons();
    }
    @GetMapping("/getallUsers")
    public List<User> getAllUsers(){
        return service.retrieveAllUsers();
    }
    @GetMapping("/getallVehicules")
    public List<Vehicule> getAllV(){
        return service.retrieveAllVehicule();
    }
    @GetMapping("/getallLogsHistorique")
    public List<LogHisorique> getAllLogs(){
        return service.GetAllLog();
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
    @PutMapping("/updateLogHistorique")
    public LogHisorique updateLogHistorique(@RequestBody LogHisorique lh){
        return  service.UpdateLog(lh);
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
    @DeleteMapping("/deleteLogHistorique/{id}")
    public void deleteLog(@PathVariable("id") Long LogID){
        service.DeleteLog(LogID);
    }
    @GetMapping("/calculPrixDelivry")
    public BigDecimal calculateDeliveryPrice(@RequestParam Long userId, @RequestParam BigDecimal baseDeliveryPrice) {
        User user = service.getUserById(userId);
        BigDecimal finalPrice = service.applyDiscounts((Client) user, baseDeliveryPrice);
        service.updateUser(user);
        return finalPrice;
    }
}
