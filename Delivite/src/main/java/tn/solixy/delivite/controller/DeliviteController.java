package tn.solixy.delivite.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.solixy.delivite.entities.*;
import tn.solixy.delivite.repositories.IImageRepository;
import tn.solixy.delivite.repositories.IUserRepository;
import tn.solixy.delivite.services.CloudinaryService;
import tn.solixy.delivite.services.IGestionDelivite;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/Delivite")
@CrossOrigin("*")
public class DeliviteController {
    IGestionDelivite service;
    CloudinaryService sc;

    @GetMapping("/role/{roleName}")
    public List<User> getUsersByRole(@PathVariable Role roleName) {
        return service.findByRole(roleName);
    }
    @GetMapping("/getClient/{id}")
    public User getClient(@PathVariable("id") Long id) {
        return service.getUserById(id);
    }
    @GetMapping("/getClients")
    public List<User> getClients() {
        return service.getAllClients(Role.CLIENT);
    }
    @GetMapping("/getChauffeurs")
    public List<User> getChauffeurs() {
        return service.getAllChauffeurs(Role.CHAUFFEUR);
    }
    @GetMapping("/getRestaurants")
    public List<User> getRestaurants() {
        return service.getAllRestaurants(Role.RESTO);
    }
//    @PostMapping("/addUser/{role}")
//    public User addUser(@PathVariable("role") Role role, @RequestBody User user) {
//        // Définir la date d'inscription
//        LocalDate currentDate = LocalDate.now();
//        Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        user.setRegistrationDate(date);
//
//        // Enregistrer l'utilisateur
//        return service.addUser(String.valueOf(role),user);
//    }
@SneakyThrows
@PostMapping("/addUser")
public ResponseEntity<String> addUserWithImage(
        @RequestParam(value = "firstName", required = true) String firstName,
        @RequestParam(value = "lastName", required = true) String lastname,
        @RequestParam(value = "password", required = true) String password,
        @RequestParam(value = "preferredLanguage", required = true) String preferredLanguage,
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "location", required = true) String location,
        @RequestParam(value = "image", required = true) MultipartFile imageFile,
        @RequestParam(value = "address", required = true) String address,
        @RequestParam(value = "date_of_birth", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateOfBirthStr,
        @RequestParam(value = "phone_number", required = true) String phone_number) {

        try {
            Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
        // Log des paramètres reçus
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastname);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
        System.out.println("preferredLanguage: " + preferredLanguage);
        System.out.println("location: " + location);
        System.out.println("address: " + address);
        System.out.println("date_of_birth: " + dateOfBirth);
        System.out.println("phone_number: " + phone_number);
        System.out.println("imageFile: " + (imageFile != null ? imageFile.getOriginalFilename() : "null"));

        // Vérifiez que tous les paramètres obligatoires sont présents
        if (firstName == null || firstName.isEmpty() ||
                lastname == null || lastname.isEmpty() ||
                password == null || password.isEmpty() ||
                preferredLanguage == null || preferredLanguage.isEmpty() ||
                email == null || email.isEmpty() ||
                location == null || location.isEmpty() ||
                imageFile == null || imageFile.isEmpty() ||
                address == null || address.isEmpty() ||
                dateOfBirth == null ||
                phone_number == null || phone_number.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tous les paramètres sont obligatoires.");
        }

        // Appel de la méthode de service pour ajouter l'utilisateur avec l'image
        service.addUserWithImage("CLIENT", firstName, lastname, password, email, preferredLanguage, location, imageFile, address, dateOfBirth, phone_number);
        return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur ajouté avec succès !");
    } catch (Exception e) {
        e.printStackTrace(); // ou tout autre traitement d'erreur approprié
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'utilisateur.");
    }
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

    @PutMapping("/updateLivraison")
    public Livraison updateLivraison(@RequestBody Livraison livraison){
        return  service.updateLivraison(livraison);
    }
    @PutMapping("/updateClient")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        // Logique pour mettre à jour le client
        Client updatedClient = service.updateClient(client);
        return ResponseEntity.ok(updatedClient);
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
//    @GetMapping("/calculPrixDelivry")
//    public BigDecimal calculateDeliveryPrice(@RequestParam Long userId, @RequestParam BigDecimal baseDeliveryPrice) {
//        User user = service.getUserById(userId);
//        BigDecimal finalPrice = service.applyDiscounts((Client) user, baseDeliveryPrice);
//        service.updateUser(user);
//        return finalPrice;
//    }
    @PutMapping("/accept-chauffeur/{id}")
    public Chauffeur acceptChauffeur(@PathVariable Long id) {
        return service.acceptChauffeur(id);
    }
}
