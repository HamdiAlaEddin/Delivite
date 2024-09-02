package tn.solixy.delivite.services;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.solixy.delivite.dto.Commandedto;
import tn.solixy.delivite.dto.LivraisonDto;
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
ContactRepository gestionContact;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        //crypt password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //add user
        iUserRepository.save(user);
    }

    @Override
    public boolean login(String username, String password) {
        User user = iUserRepository.findByEmail(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Successful login
            return true;
        }
        return false;
    }
    @Override
    public User getUserByEmail(String email) {
        return iUserRepository.findByEmail(email);
    }
    @Override
    public boolean resetPassword(long userId, String newPassword) {
        User user = iUserRepository.findById(userId).orElse(null);
        if (user != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            iUserRepository.save(user);
            return true;
        }
        return false;
    }
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
public Client updateClient(Client client) {

        Long clientId = client.getUserID();

        Optional<User> existingUserOptional = iUserRepository.findById(clientId);

        if (existingUserOptional.isPresent() && existingUserOptional.get() instanceof Client) {
            Client existingClient = (Client) existingUserOptional.get();

            // Mettre à jour uniquement les champs modifiables
            if (client.getFirstName() != null) {
                existingClient.setFirstName(client.getFirstName());
            }
            if (client.getLastName() != null) {
                existingClient.setLastName(client.getLastName());
            }
            if (client.getPassword() != null) {
                existingClient.setPassword(client.getPassword());
            }
            if (client.getEmail() != null) {
                existingClient.setEmail(client.getEmail());
            }
            if (client.getAddress() != null) {
                existingClient.setAddress(client.getAddress());
            }
            if (client.getPhoneNumber() != null) {
                existingClient.setPhoneNumber(client.getPhoneNumber());
            }
            if (client.getImage() != null) {
                existingClient.setImage(client.getImage());
            }
            if (client.getPreferredLanguage() != null) {
                existingClient.setPreferredLanguage(client.getPreferredLanguage());
            }
            if (client.getDateOfBirth() != null) {
                existingClient.setDateOfBirth(client.getDateOfBirth());
            }

            return iUserRepository.save(existingClient);
        } else {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }
    }
    @Override
    public Resto updateResto(Resto rs) {

        Long Id = rs.getUserID();

        Optional<User> existingUserOptional = iUserRepository.findById(Id);

        if (existingUserOptional.isPresent() && existingUserOptional.get() instanceof Resto) {
            Resto existingRS = (Resto) existingUserOptional.get();

            // Mettre à jour uniquement les champs modifiables
            if (rs.getFirstName() != null) {
                existingRS.setFirstName(rs.getFirstName());
            }
            if (rs.getLastName() != null) {
                existingRS.setLastName(rs.getLastName());
            }
            if (rs.getPassword() != null) {
                existingRS.setPassword(rs.getPassword());
            }
            if (rs.getEmail() != null) {
                existingRS.setEmail(rs.getEmail());
            }
            if (rs.getAddress() != null) {
                existingRS.setAddress(rs.getAddress());
            }
            if (rs.getPhoneNumber() != null) {
                existingRS.setPhoneNumber(rs.getPhoneNumber());
            }
            if (rs.getImage() != null) {
                existingRS.setImage(rs.getImage());
            }
            if (rs.getPreferredLanguage() != null) {
                existingRS.setPreferredLanguage(rs.getPreferredLanguage());
            }
            if (rs.getDateOfBirth() != null) {
                existingRS.setDateOfBirth(rs.getDateOfBirth());
            }

            return iUserRepository.save(existingRS);
        } else {
            throw new EntityNotFoundException("Resto not found with id: " + Id);
        }
    }
    @Override
            public Admin updateAdmin(Admin admin) {

        Long id = admin.getUserID();

        Optional<User> existingUserOptional = iUserRepository.findById(id);

        if (existingUserOptional.isPresent() && existingUserOptional.get() instanceof Admin) {
            Admin existingAdmin = (Admin) existingUserOptional.get();

            // Mettre à jour uniquement les champs modifiables
            if (admin.getFirstName() != null) {
                existingAdmin.setFirstName(admin.getFirstName());
            }
            if (admin.getLastName() != null) {
                existingAdmin.setLastName(admin.getLastName());
            }
            if (admin.getPassword() != null) {
                existingAdmin.setPassword(admin.getPassword());
            }
            if (admin.getEmail() != null) {
                existingAdmin.setEmail(admin   .getEmail());
            }
            if (admin.getAddress() != null) {
                existingAdmin.setAddress(admin.getAddress());
            }
            if (admin.getPhoneNumber() != null) {
                existingAdmin.setPhoneNumber(admin.getPhoneNumber());
            }
            if (admin.getImage() != null) {
                existingAdmin.setImage(admin.getImage());
            }
            if (admin.getPreferredLanguage() != null) {
                existingAdmin.setPreferredLanguage(admin.getPreferredLanguage());
            }
            if (admin.getDateOfBirth() != null) {
                existingAdmin.setDateOfBirth(admin.getDateOfBirth());
            }

            return iUserRepository.save(existingAdmin);
        } else {
            throw new EntityNotFoundException("Client not found with id: " + id);
        }
    }
    @Override
    public Chauffeur updateChauffeur(Chauffeur chauffeur) {
        Long id = chauffeur.getUserID();
        Optional<User> existingUserOptional = iUserRepository.findById(id);

        if (existingUserOptional.isPresent() && existingUserOptional.get() instanceof Chauffeur) {
            Chauffeur existingChauffeur = (Chauffeur) existingUserOptional.get();

            // Mettre à jour uniquement les champs modifiables
            if (chauffeur.getFirstName() != null) {
                existingChauffeur.setFirstName(chauffeur.getFirstName());
            }
            if (chauffeur.getLastName() != null) {
                existingChauffeur.setLastName(chauffeur.getLastName());
            }
            if (chauffeur.getPassword() != null) {
                existingChauffeur.setPassword(chauffeur.getPassword());
            }
            if (chauffeur.getEmail() != null) {
                existingChauffeur.setEmail(chauffeur.getEmail());
            }
            if (chauffeur.getAddress() != null) {
                existingChauffeur.setAddress(chauffeur.getAddress());
            }
            if (chauffeur.getPhoneNumber() != null) {
                existingChauffeur.setPhoneNumber(chauffeur.getPhoneNumber());
            }
            if (chauffeur.getImage() != null) {
                existingChauffeur.setImage(chauffeur.getImage());
            }
            if (chauffeur.getPreferredLanguage() != null) {
                existingChauffeur.setPreferredLanguage(chauffeur.getPreferredLanguage());
            }
            if (chauffeur.getDateOfBirth() != null) {
                existingChauffeur.setDateOfBirth(chauffeur.getDateOfBirth());
            }
            if (chauffeur.getNumPermisConduit() != null) {
                existingChauffeur.setNumPermisConduit(chauffeur.getNumPermisConduit());
            }
            if (chauffeur.getLivraisons() != null) {
                existingChauffeur.setLivraisons(chauffeur.getLivraisons());
            }

            if (chauffeur.getLocation() != null) {
                existingChauffeur.setLocation(chauffeur.getLocation());
            }

            return iUserRepository.save(existingChauffeur);
        } else {
            throw new EntityNotFoundException("Chauffeur not found with id: " + id);
        }
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
            if (user.getImage() != null) {
                String imageURL = user.getImage().getImageURL();
                Long imageId = user.getImage().getId();
                deleteImageFromCloudinary(imageURL);
            }
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
    @Transactional
    public ResponseEntity<String> addChauffeurWithImage(String userType, String firstName, String lastName, String password, String email,
                                                   String preferredLanguage, String location, MultipartFile imageFile, String address, Date date_of_birth, String phone_number, String numPermisConduit) {

        User user =new Chauffeur();

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
        if (user instanceof Chauffeur) {
            Chauffeur chauffeur = (Chauffeur) user;
            chauffeur.setNumPermisConduit(numPermisConduit); // Utilisez l'attribut spécifique à Chauffeur
        } else {
            // Gérez le cas où l'objet n'est pas une instance de Chauffeur
            System.out.println("L'objet n'est pas une instance de Chauffeur");
        }

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
        livraison.setDateLivraison(currentDate);
        return  iLivraisonRepository.save(livraison);
    }
    @Override
    public List<User> findByRole(Role role) {
        return iUserRepository.findByRole(role);
    }
    @Override
    public LogHisorique addLog(LogHisorique lh) {
        Livraison livrai = iLivraisonRepository.findById(lh.getLivr().getLivraisonID())
                .orElseThrow(() -> new EntityNotFoundException("Livraison not found"));
        Vehicule vehicule = iVehiculeRepository.findById(lh.getVehic().getVehiculeID())
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found"));

        // Assigner les entités au LogHistorique
        lh.setLivr(livrai);
        lh.setVehic(vehicule);

        // Sauvegarder le LogHistorique
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

    public Chauffeur assignChauffeur(Client client) {
        // Récupérer tous les chauffeurs acceptés et disponibles
        List<Chauffeur> chauffeursDisponibles = iUserRepository.findByAcceptedTrueAndDisponibleTrue();

        // Si aucun chauffeur n'est disponible, retourner null ou gérer le cas différemment
        if (chauffeursDisponibles.isEmpty()) {
            return null; // Ou vous pouvez lever une exception ou retourner un message d'erreur
        }

        // Filtrer les chauffeurs par leur note (optionnel)
        Chauffeur chauffeurAvecMeilleureNote = chauffeursDisponibles.stream()
                .max(Comparator.comparingDouble(this::calculateAverageRating))
                .orElse(chauffeursDisponibles.get(0)); // Prendre le premier disponible s'il n'y a pas de notes

        // Marquer le chauffeur comme indisponible
        chauffeurAvecMeilleureNote.setDisponible(false);
        iUserRepository.save(chauffeurAvecMeilleureNote);

        return chauffeurAvecMeilleureNote;
    }

    // Méthode pour calculer la note moyenne d'un chauffeur
    private double calculateAverageRating(Chauffeur chauffeur) {
        return chauffeur.getNotes().stream()
                .mapToDouble(note -> note.getRate().getValue()) // Map enum to its numeric value
                .average()
                .orElse(0.0);
    }
/////////////////////////////////////////////////////////////
//@Override
//public List<LivraisonDto> getAlLivraisons() {
//    List<Livraison> livraisons = iLivraisonRepository.findAll();
//    return livraisons.stream()
//            .map(LivraisonMapper::toDto)
//            .collect(Collectors.toList());
//}

    @Override
public void processLivraisonRequest(Commandedto request) {
    // Vérifiez si le client et le restaurant existent
    Client client = (Client) iUserRepository.findByEmail(request.getClientmail());
    Resto resto = (Resto) iUserRepository.findByEmail(request.getRestomail());


    // Créez la livraison
    Livraison livraison = new Livraison();
    livraison.setCli(client);
    livraison.setAdresseLivraison(client.getAddress()); // Assurez-vous que l'adresse du client est correcte
    livraison.setType(request.getTypelivraison());
    livraison.setPrix(request.getPrice());
    livraison.setDescription(request.getDescription());
    livraison.setStatus(StatusLivraison.EnAttente);
    livraison.setPaiement(request.getTypePayement());
    livraison.setVehicule(getVehiculeById(1L));
    // Trouver un chauffeur disponible et capable
    Chauffeur chauffeur = trouverChauffeurDisponible(livraison);

    if (chauffeur != null) {
        // Assignez le chauffeur à la livraison
        livraison.setChauf(chauffeur);
        livraison.setStatus(StatusLivraison.EnRoute);
        LocalDate currentDate = LocalDate.now();
        Date registrationDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        livraison.setDateCommande(currentDate);
        livraison.setPosition(chauffeur.getLocation());
        iLivraisonRepository.save(livraison);
        client.setDeliveriesCount(client.getDeliveriesCount() + 1);

        // Notifiez le chauffeur
        envoyerNotificationAuChauffeur(chauffeur, livraison);
    } else {
        throw new RuntimeException("Aucun chauffeur disponible pour cette livraison");
    }
}
    private Chauffeur trouverChauffeurDisponible(Livraison livraison) {
        // Trouver un chauffeur disponible et accepté
        return iUserRepository.findByAcceptedTrueAndDisponibleTrue().stream().findFirst().orElse(null);
    }

    public void envoyerNotificationAuChauffeur(Chauffeur chauffeur, Livraison livraison) {
        // Construisez le message de notification
        String notificationMessage = "Nouvelle livraison ID: " + livraison.getLivraisonID() +
                " pour vous, " + chauffeur.getFirstName();

        // Envoyez la notification à tous les clients abonnés à "/topic/notifications"
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }



    ///////////////////////////////////////////////////////

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
    public List<User> getAllChauffeurs(Role CHAUFFEUR) {
        List<User> allChauffeurs=iUserRepository.findByRole(CHAUFFEUR);
        return allChauffeurs;
    }
    @Override
    public List<User> getAllAdmins(Role ADMIN) {
        List<User> allADMINS=iUserRepository.findByRole(ADMIN);
        return allADMINS;
    }

    @Override
    public Chauffeur acceptChauffeur(Long id) {
        Chauffeur chauffeur = (Chauffeur) iUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chauffeur not found"));
        chauffeur.setAccepted(!chauffeur.isAccepted());
        return iUserRepository.save(chauffeur);
    }
    @Override
    public Chauffeur SetChauffeurdispo(Long id) {
        Chauffeur chauffeur = (Chauffeur) iUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chauffeur not found"));
        chauffeur.setDisponible(!chauffeur.isDisponible());
        return iUserRepository.save(chauffeur);
    }
    @Override
    public LivraisonDto getLivraisonDTOById(Long id) {
        Livraison livraison = iLivraisonRepository.findById(id).orElse(null);
        if (livraison != null) {
            // Assurez-vous que les méthodes appelées sur les objets non nulls
            return new LivraisonDto(
                    livraison.getLivraisonID(),
                    livraison.getStatus() != null ? livraison.getStatus().toString() : null,
                    livraison.getType() != null ? livraison.getType().toString() : null,
                    livraison.getPaiement() != null ? livraison.getPaiement().toString() : null,
                    livraison.getDateCommande(),
                    livraison.getDateLivraison(),
                    livraison.getAdresseLivraison(),
                    livraison.getCli() != null ? livraison.getCli().getFirstName() : null,
                    livraison.getCli() != null ? livraison.getCli().getPhoneNumber() : null,
                    livraison.getPosition(),
                    livraison.getPrix(),
                    livraison.getDescription(),
                    livraison.getChauf() != null ? livraison.getChauf().getFirstName() : null,
                    livraison.getChauf() != null ? livraison.getChauf().getPhoneNumber() : null,
                    livraison.getVehicule() != null ? livraison.getVehicule().getImmatriculation() : null
            );
        }
        return null;
    }
    @Override
    public List<LivraisonDto> getAllLivraisonDTOs() {
        List<Livraison> livraisons = iLivraisonRepository.findAll();
        return livraisons.stream().map(livraison -> new LivraisonDto(
                livraison.getLivraisonID(),
                livraison.getStatus() != null ? livraison.getStatus().toString() : null,
                livraison.getType() != null ? livraison.getType().toString() : null,
                livraison.getPaiement() != null ? livraison.getPaiement().toString() : null,
                livraison.getDateCommande(),
                livraison.getDateLivraison(),
                livraison.getAdresseLivraison(),
                livraison.getCli() != null ? livraison.getCli().getFirstName() : null,
                livraison.getCli() != null ? livraison.getCli().getPhoneNumber() : null,
                livraison.getPosition(),
                livraison.getPrix(),
                livraison.getDescription(),
                livraison.getChauf() != null ? livraison.getChauf().getFirstName() : null,
                livraison.getChauf() != null ? livraison.getChauf().getPhoneNumber() : null,
                livraison.getVehicule() != null ? livraison.getVehicule().getImmatriculation() : null
        )).collect(Collectors.toList());
    }
    @Override
    public List<LivraisonDto> getLivraisonsByUserId(Long userId) {
        // Utiliser l'une des méthodes du repository pour récupérer les livraisons
        List<Livraison> livraisons = iLivraisonRepository.findBylivraisonbyUserId(userId);

        // Mapper les entités Livraison en DTOs
        return livraisons.stream().map(livraison -> new LivraisonDto(
                livraison.getLivraisonID(),
                livraison.getStatus() != null ? livraison.getStatus().toString() : null,
                livraison.getType() != null ? livraison.getType().toString() : null,
                livraison.getPaiement() != null ? livraison.getPaiement().toString() : null,
                livraison.getDateCommande(),
                livraison.getDateLivraison(),
                livraison.getAdresseLivraison(),
                livraison.getCli() != null ? livraison.getCli().getFirstName() : null,
                livraison.getCli() != null ? livraison.getCli().getPhoneNumber() : null,
                livraison.getPosition(),
                livraison.getPrix(),
                livraison.getDescription(),
                livraison.getChauf() != null ? livraison.getChauf().getFirstName() : null,
                livraison.getChauf() != null ? livraison.getChauf().getPhoneNumber() : null,
                livraison.getVehicule() != null ? livraison.getVehicule().getImmatriculation() : null
        )).collect(Collectors.toList());
    }
    @Override
    public Contact addcontact(Contact contact) {
        contact.setDate(LocalDate.now());
         String email= contact.getMail();
          sendContactNotification(email);
        return gestionContact.save(contact);
    }

    @Override
    public void removeContact(Long id_contact) {
        gestionContact.deleteById(id_contact);

    }

    @Override
    public List<Contact> retrieveContact() {
        return gestionContact.findAllByOrderByDateDesc();
    }
    @Autowired
    private JavaMailSender emailSender;
    public  void sendContactNotification(String toEmail) {
        SimpleMailMessage message  = new SimpleMailMessage();
        message.setFrom("*-----*-----*----*");
        message.setSubject("Confirmation de réception de votre Contact");
        message.setTo(toEmail);
        message.setText(  "\"Cher utilisateur,\" \n" +
                "                \"Nous avons bien reçu votre CONTACT et nous vous en remercions. Votre opinion est précieuse pour nous \" \n" +
                "                \"et nous l'utiliserons pour améliorer nos services. Si vous avez d'autres questions ou commentaires, \" \n" +
                "                \"n'hésitez pas à nous contacter.\"\n" +
                "                \"Cordialement,\" \n" +
                "                \"DELIVITE\"");
        emailSender.send(message);
    }
}

