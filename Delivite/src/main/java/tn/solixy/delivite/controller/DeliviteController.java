package tn.solixy.delivite.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.solixy.delivite.Auth.JwtUtil;
import tn.solixy.delivite.dto.Commandedto;
import tn.solixy.delivite.dto.LoginRequest;
import tn.solixy.delivite.dto.ResetPasswordRequest;
import tn.solixy.delivite.entities.*;
import tn.solixy.delivite.services.*;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/Delivite")
@CrossOrigin("*")
public class DeliviteController {
    IGestionDelivite service;
    CloudinaryService sc;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwt;
    @PostMapping("/authenticate_user")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println(loginRequest.getUsername()+loginRequest.getPassword());
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

            if(authentication.isAuthenticated()){
                //Create UserDetails for token creation
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
                User user = service.getUserByEmail(loginRequest.getUsername());
                //Create token
                String token = jwt.createToken(userDetails);
                map.put("status", 200);
                map.put("message", "success");
                map.put("token",token);
                map.put("role", user.getRole());
                return new ResponseEntity<Object>(map, HttpStatus.OK);
                //return ResponseEntity.ok(token);
            }else {
                map.put("status", 400);
                map.put("message", "There was a problem");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }
        }catch( BadCredentialsException ex){
            map.put("message", "Bad credentials");
            map.put("status", 401);
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }catch ( LockedException e){
            map.put("message", "Your account is locked");
            map.put("status", 401);
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }catch ( DisabledException e){
            map.put("message", "Your account is disabled");
            map.put("status", 401);
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register_user")
    public ResponseEntity<?> register(@RequestBody Client user) {
        Map<String, Object> map = new HashMap<String, Object>();
        // Check if the user with the same email already exists
        if (service.getUserByEmail(user.getEmail()) != null) {
            // User with the same email already exists
            map.put("message", "You already have an account");
            map.put("status", 401);
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }

        // add user to database
        service.createUser(user);

        // Registration successful
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        //Create token
        String token = jwt.createToken(userDetails);
        map.put("message", "Account created successfully");
        map.put("status", 201);
        map.put("token",token);
        return new ResponseEntity<Object>(map, HttpStatus.CREATED);
    }

    @PostMapping("/getbytoken")
    public User getUserByToken(@RequestBody String token) {
        String email = jwt.getEmailFromToken(token);
        return service.getUserByEmail(email);
    }
    @PostMapping("/resetpassword")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest request){
        if(jwt.isPasswordToken(request.getToken())){
            String email;
            try{
                email = jwt.getEmailFromToken(request.getToken());
            } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException |
                     MalformedJwtException exception){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = service.getUserByEmail(email);
            service.resetPassword(user.getUserID(),request.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/resetpasswordrequest")
    public ResponseEntity resetPasswordRequest(@RequestBody String email){
        User user = service.getUserByEmail(email);
        System.out.println("email: "+email);
        System.out.println("user: "+user);
        String token = jwt.createPasswordToken(userDetailsService.loadUserByUsername(email));
        System.out.println("token: "+token);
        //mailer.sendForgotPasswordEmail(user, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/resetpassword")
    public ResponseEntity adminResetPassword(@RequestBody Long id){
        Map<String, Object> map = new HashMap<String, Object>();
        User user = service.getUserById(id);
        System.out.println(user);
        String token = jwt.createPasswordToken(userDetailsService.loadUserByUsername(user.getEmail()));
        System.out.println(token);
        map.put("token",token);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @PostMapping("/admin/getbytoken")
    public ResponseEntity getAdminByToken(@RequestBody String token) {
        String email = jwt.getEmailFromToken(token);
        User user = service.getUserByEmail(email);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name",user.getFirstName());
        map.put("role","ADMIN");
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
    @GetMapping("/role/{roleName}")
    public List<User> getUsersByRole(@PathVariable Role roleName) {
        return service.findByRole(roleName);
    }
    @GetMapping("/getUser/{id}")
    public User getClient(@PathVariable("id") Long id) {
        return service.getUserById(id);
    }
    @GetMapping("/getClients")
    public List<User> getClients() {
        return service.getAllClients(Role.CLIENT);
    }
    @GetMapping("/getAdmins")
    public List<User> getadmins() {
        return service.getAllAdmins(Role.ADMIN);
    }
    @GetMapping("/getVehicule")
    public List<Vehicule> getVehic() {
        return service.retrieveAllVehicule();
    }
    @GetMapping("/getVehicule/{id}")
    public Vehicule getVehicule(@PathVariable("id") Long id) {
        return service.getVehiculeById(id);
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
    @PostMapping("/addRestaurant")
    public ResponseEntity<String> addRestoWithImage(
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
            service.addUserWithImage("RESTO", firstName, lastname, password, email, preferredLanguage, location, imageFile, address, dateOfBirth, phone_number);
            return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace(); // ou tout autre traitement d'erreur approprié
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du Restaurant.");
        }
    }
    @SneakyThrows
    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdminWithImage(
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
            service.addUserWithImage("ADMIN", firstName, lastname, password, email, preferredLanguage, location, imageFile, address, dateOfBirth, phone_number);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace(); // ou tout autre traitement d'erreur approprié
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'Admin.");
        }
    }
    @PostMapping("/addChauffeur")
    public ResponseEntity<String> addChauffeurWithImage(
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "lastName", required = true) String lastname,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "preferredLanguage", required = true) String preferredLanguage,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "location", required = true) String location,
            @RequestParam(value = "image", required = true) MultipartFile imageFile,
            @RequestParam(value = "address", required = true) String address,
            @RequestParam(value = "date_of_birth", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateOfBirthStr,
            @RequestParam(value = "phone_number", required = true) String phone_number,
    @RequestParam(value = "numPermisConduit", required = true) String numPermisConduit){

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
            System.out.println("numPermisConduit: " + numPermisConduit);
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
                    phone_number == null || phone_number.isEmpty()||
                    numPermisConduit == null || numPermisConduit.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tous les paramètres sont obligatoires.");
            }

            // Appel de la méthode de service pour ajouter l'utilisateur avec l'image
            service.addChauffeurWithImage("Chauffeur", firstName, lastname, password, email, preferredLanguage, location, imageFile, address, dateOfBirth, phone_number,numPermisConduit);
            return ResponseEntity.status(HttpStatus.CREATED).body("Chauffeur ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace(); // ou tout autre traitement d'erreur approprié
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du Chauffeur.");
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
    @GetMapping("/getallCommande")
    public ResponseEntity<List<Commandedto>> getAllLivraisons() {
        List<Livraison> livraisons = service.retrieveAllLivraisons();
        List<Commandedto> dtos = livraisons.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
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
    private Commandedto mapToDto(Livraison livraison) {
        String vehiculeImmat = ""; // Initialiser avec une valeur par défaut
        if (livraison.getVehicule() != null ) {
            // Suppose que nous voulons l'immatriculation du premier véhicule associé
            vehiculeImmat = livraison.getVehicule().getImmatriculation();
        }

        return new Commandedto(
                livraison.getLivraisonID(),
                livraison.getStatus(),
                livraison.getType(),
                livraison.getPaiement(),
                livraison.getDateCommande(),
                livraison.getDateLivraison(),
                livraison.getAdresseLivraison(),
                livraison.getCli().getFirstName(),
                livraison.getCli().getLastName(),
                livraison.getPosition(),
                livraison.getPrix(),
                livraison.getDescription(),
                livraison.getChauf().getFirstName(),
                livraison.getChauf().getLastName(),
               livraison.getVehicule().getImmatriculation()
        );
    }
    @PutMapping("/updateRestaurant")
    public ResponseEntity<Resto> updateRestaurant(@RequestBody Resto rs) {
        // Logique pour mettre à jour le client
        Resto updatedResto = service.updateResto(rs);
        return ResponseEntity.ok(updatedResto);
    }
    @PutMapping("/updateChauffeur")
    public ResponseEntity<Chauffeur> updateChauffeur(@RequestBody Chauffeur chauffeur) {
        // Logique pour mettre à jour le chauffeur
        Chauffeur updatedChauffeur = service.updateChauffeur(chauffeur);
        return ResponseEntity.ok(updatedChauffeur);
    }
    @PutMapping("/updateAdmin")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        // Logique pour mettre à jour le chauffeur
        Admin updatedAdmin = service.updateAdmin(admin);
        return ResponseEntity.ok(updatedAdmin);
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
