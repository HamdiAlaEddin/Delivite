package tn.solixy.delivite.services;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.solixy.delivite.entities.*;
import tn.solixy.delivite.repositories.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class GestionDelivite implements IGestionDelivite {

    ILivraisonRepository iLivraisonRepository;
    IUserRepository iUserRepository;
    IVehiculeRepository iVehiculeRepository;
    ILogHistorique iLogHistorique;
     CloudinaryService cloudinaryService;
     IImageRepository imageRepository;
     NoteRepository noteRepository;

   @Override
   public Livraison getLivraisonById(Long idL) {
       return iLivraisonRepository.findById(idL).get();
   }
    @Override
    public User getUserById(Long idU) {
        return iUserRepository.findById(idU).get();
    }
    @Override
    public Vehicule getVehiculeById(Long idV) {
        return iVehiculeRepository.findById(idV).get();
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

        Optional<User> USOptional = iUserRepository.findById(Uid);
        if (USOptional.isPresent()) {
            User user = USOptional.get();
            String imageURL = user.getImage().getImageURL();
            Long imageId = user.getImage().getId();
            deleteImageFromCloudinary(imageURL);
            iUserRepository.deleteById(Uid);
        }

    }
    @Override
    public void DeleteLivraison(Long Lid) {
        iLivraisonRepository.deleteById(Lid);
    }
    @Override
    public void DeleteVehicule(Long Vid) {
            iVehiculeRepository.deleteById(Vid);}
    @Override
    public void deleteImageFromCloudinary(String imageUrl) {
        try {
            // 5oudh imageID from URL
            String imageId = extractImageIdFromUrl(imageUrl);
            // Supprimer image from Cloudinary
            cloudinaryService.cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  String extractImageIdFromUrl(String imageUrl) {
        // positionner id mel URl
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        int lastDotIndex = imageUrl.lastIndexOf(".");
        // extract imageID from URL
        return imageUrl.substring(lastSlashIndex + 1, lastDotIndex);
    }
    @Override
    public User addUser(String userType,User user) {

            switch (userType.toUpperCase()) {
                case "CLIENT":
                    user = new Client();
                    break;
                case "CHAUFFEUR":
                    user = new Chauffeur();
                    break;
                case "RESTO":
                    user = new Resto();
                    break;
                case "ADMIN":
                    user = new Admin();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user type: " + userType);
            }
        return iUserRepository.save(user);
    }
//    @Transactional
//    public ResponseEntity<String> addUserWithImage(User user, MultipartFile imageFile) {
//                // Enregistrer l'image sur Cloudinary
//        Map uploadResult = null;
//        try {
//            uploadResult = cloudinaryService.upload(imageFile);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        // Récupérer l'URL de l'image depuis Cloudinary
//                String imageUrl = (String) uploadResult.get("url");
//                Image image = new Image();
//                image.setName(imageFile.getOriginalFilename());
//                image.setImageURL(imageUrl);
//                imageRepository.save(image);
//                user.setImage(image);
//                user.setRole(Role.CLIENT);
//                LocalDate currentDate = LocalDate.now();
//                Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//                user.setRegistrationDate(date);
//                iUserRepository.save(user);
//                return new ResponseEntity<>("User added successfully", HttpStatus.OK);
//            }
@Transactional
public ResponseEntity<String> addUserWithImage(String userType, String firstName, String lastName, String password, String email,
                                               String preferredLanguage, String location, MultipartFile imageFile, String address, Date date_of_birth, String phone_number) {

    User user;

    // Gérer le type d'utilisateur
    switch (userType.toUpperCase()) {
        case "CLIENT":
            user = new Client();
            user.setRole(Role.CLIENT);
            break;
        case "CHAUFFEUR":
            user = new Chauffeur();
            user.setRole(Role.CHAUFFEUR);
            break;
        case "RESTO":
            user = new Resto();
            user.setRole(Role.RESTO);
            break;
        case "ADMIN":
            user = new Admin();
            user.setRole(Role.ADMIN);
            break;
        default:
            throw new IllegalArgumentException("Invalid user type: " + userType);
    }

    // Remplir les données communes
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPassword(password);
    user.setEmail(email);
    user.setPreferredLanguage(preferredLanguage);
    user.setLocation(location);
    user.setAddress(address);
    user.setDateOfBirth(date_of_birth);
    user.setPhoneNumber(phone_number);

    // Enregistrer l'image sur Cloudinary
    Map<String, Object> uploadResult;
    try {
        uploadResult = cloudinaryService.upload(imageFile);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    // Récupérer l'URL de l'image depuis Cloudinary
    String imageUrl = (String) uploadResult.get("url");
    if (imageUrl == null || imageUrl.isEmpty()) {
        throw new IllegalArgumentException("L'URL de l'image est nulle ou vide.");
    }
    Image image = new Image();
    image.setName(imageFile.getOriginalFilename());
    image.setImageURL(imageUrl);
    imageRepository.save(image);

    // Associer l'image à l'utilisateur
    user.setImage(image);

    // Enregistrer la date d'inscription
    LocalDate currentDate = LocalDate.now();
    Date registrationDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    user.setRegistrationDate(registrationDate);

    // Enregistrer l'utilisateur dans le dépôt
    iUserRepository.save(user);

    return new ResponseEntity<>("Utilisateur ajouté avec succès", HttpStatus.CREATED);
}


    @Override
    public Vehicule addVehicule(Vehicule vehicule) {
        return iVehiculeRepository.save(vehicule);
    }
    @Override
    public Livraison addLivraison(Livraison livraison){
        LocalDate currentDate = LocalDate.now();
        Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        livraison.setDateLivraison(date);
        return  iLivraisonRepository.save(livraison);
    }
    @Override
    public List<User> findByRole(Role role) {
        return iUserRepository.findByRole(role);
    }
    @Override
    public LogHisorique addLog(LogHisorique lh) {
        return iLogHistorique.save(lh);
    }
    @Override
    public List<LogHisorique> GetAllLog() {
        return iLogHistorique.findAll();
    }
    @Override
    public LogHisorique GetLogbyId(Long ilh) {
        return iLogHistorique.findById(ilh).get();
    }
    @Override
    public LogHisorique UpdateLog(LogHisorique lh) {
        return iLogHistorique.save(lh);
    }
    @Override
    public void DeleteLog(Long ilh) {
      iLogHistorique.deleteById(ilh);
    }
    @Override
    public void donnerNote(Long clientId, Long chauffeurId, Rating rating) {
        Client client = iUserRepository.findById(clientId)
                .filter(User -> User instanceof Client)
                .map(Client.class::cast)
                .orElse(null);

        Chauffeur chauffeur = iUserRepository.findById(chauffeurId)
                .filter(User -> User instanceof Chauffeur)
                .map(Chauffeur.class::cast)
                .orElse(null);

        if (client != null && chauffeur != null) {
            Note note = new Note();
            note.setClient(client);
            note.setChauffeur(chauffeur);
            note.setRate(rating);

            chauffeur.addNote(note);
            iUserRepository.save(chauffeur);
        }
    }
   /*   @Override
    public Optional<Chauffeur> choisirChauffeur(String location) {
        List<Chauffeur> chauffeurs = iUserRepository.findByDisponibleTrueAndAcceptedTrueOrderByNoteDesc();
        if (!chauffeurs.isEmpty()) {
            return Optional.of(chauffeurs.get(0));
        }
        return Optional.empty();
    }
  @Override
    public List<Vehicule> findAvailableVehicles() {
        List<Vehicule> vehiclesNotAssignedToActiveDelivery = iVehiculeRepository.findAll();
        return vehiclesNotAssignedToActiveDelivery.stream()
                .filter(vehicule -> vehicule.getChauffeurs().stream()
                        .anyMatch(chauffeur -> chauffeur.isDisponible() && chauffeur.isAccepted()))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Livraison> affecterLivraison(Long livraisonId) {
        Optional<Livraison> livraisonOpt = iLivraisonRepository.findById(livraisonId);
        if (livraisonOpt.isPresent()) {
            Livraison livraison = livraisonOpt.get();
            List<Vehicule> vehiculesDisponibles = findAvailableVehicles();
            if (!vehiculesDisponibles.isEmpty()) {
                Vehicule vehicule = vehiculesDisponibles.get(0);
                Chauffeur chauffeur = vehicule.getChauffeurs().stream()
                        .filter(c -> c.isDisponible() && c.isAccepted())
                        .findFirst()
                        .orElse(null);
                if (chauffeur != null) {
                    livraison.setId_vehicule(vehicule.getVehiculeID());
                    livraison.setId_chauffeur(chauffeur.getUserID());
                    iLivraisonRepository.save(livraison);
                    return Optional.of(livraison);
                }
            }
        }
        return Optional.empty();
   }*/
    @Override
   public BigDecimal applyDiscounts(Client user, BigDecimal deliveryPrice) {
       LocalDate today = LocalDate.now();
       LocalDate birthday = user.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       // Réduction d'anniversaire
       if (today.getMonth() == birthday.getMonth() && today.getDayOfMonth() == birthday.getDayOfMonth()) {
           deliveryPrice = deliveryPrice.multiply(BigDecimal.valueOf(0.8)); // Réduction de 20%
       }
       // Réduction tous les 3 mois
       LocalDate lastDiscountDate = user.getLastQuarterlyDiscountDate() != null ?
               user.getLastQuarterlyDiscountDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
       if (lastDiscountDate == null || today.isAfter(lastDiscountDate.plusMonths(3))) {
           deliveryPrice = deliveryPrice.multiply(BigDecimal.valueOf(0.98)); // Réduction de 2%
           user.setLastQuarterlyDiscountDate(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
       }
       // Réduction après chaque 25 livraisons
       if (user.getDeliveriesCount() > 0 && user.getDeliveriesCount() % 25 == 0) {
           deliveryPrice = deliveryPrice.multiply(BigDecimal.valueOf(0.98)); // Réduction de 2%
       }
       return deliveryPrice;
   }
   @Override
    public List<User> getAllClients(Role Client) {
       List<User> allClients=iUserRepository.findByRole(Client);
        return allClients;
    }
    @Override
    public List<User> getAllRestaurants(Role Resto) {
        List<User> allRestaurants=iUserRepository.findByRole(Resto);
        return allRestaurants;
    }
    @Override
    public List<User> getAllChauffeurs(Role Chauffeur) {
        List<User> allChauffeurs=iUserRepository.findByRole(Chauffeur);
        return allChauffeurs;
    }

    @Override
    public Chauffeur acceptChauffeur(Long id) {
        Chauffeur chauffeur = (Chauffeur) iUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chauffeur not found"));
        chauffeur.setAccepted(true);
        return iUserRepository.save(chauffeur);
    }
}

