package tn.solixy.delivite.services;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.solixy.delivite.dto.Commandedto;
import tn.solixy.delivite.dto.LivraisonDto;
import tn.solixy.delivite.entities.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
public interface IGestionDelivite {
     void createUser(User user);
     boolean login(String username, String password);
    boolean resetPassword(long userId, String newPassword);
    User getUserByEmail(String email);
    User addUser(String userType,User user);
    //ResponseEntity<String> addUserWithImage(String userType,User user, MultipartFile imageFile);
    Vehicule addVehicule(Vehicule vehicule);
     Livraison addLivraison(Livraison livraison);
     LogHisorique addLog(LogHisorique lh);
    List<LogHisorique> GetAllLog();
    LogHisorique GetLogbyId(Long ilh);
    LogHisorique UpdateLog(LogHisorique lh);
    void DeleteLog(Long ilh);
    Livraison getLivraisonById(Long idL);
    User getUserById(Long idU);
    Vehicule getVehiculeById(Long idV);
    List<User> retrieveAllUsers();
    List<Livraison> retrieveAllLivraisons();
    List<Vehicule> retrieveAllVehicule();
    //public void updateUser(User user);
     Client updateClient(Client user) ;
     Chauffeur updateChauffeur(Chauffeur chauffeur);
    Admin updateAdmin(Admin admin);
     Resto updateResto(Resto rs);
    Livraison updateLivraison(Livraison l);
    Vehicule updateVehicule(Vehicule v);
    void DeleteUser(Long Uid);
    void DeleteLivraison(Long Lid);
    void DeleteVehicule(Long Vid);
    public List<User> findByRole(Role role);
    void deleteImageFromCloudinary(String imageUrl);
    String extractImageIdFromUrl(String imageUrl);
     void donnerNote(Long clientId, Long chauffeurId, Rating valeur);
    /* public Optional<Chauffeur> choisirChauffeur(String location);
   public List<Vehicule> findAvailableVehicles();
     Optional<Livraison> affecterLivraison(Long livraisonId);*/
     BigDecimal applyDiscounts(Client user, BigDecimal deliveryPrice);
     List<User> getAllClients(Role Client);
     List<User> getAllRestaurants(Role Resto);
     List<User> getAllChauffeurs(Role CHAUFFEUR);
     List<User> getAllAdmins(Role ADMINS);
     Chauffeur acceptChauffeur(Long id);
    public void processLivraisonRequest(Commandedto request);
     ResponseEntity<String> addUserWithImage(String userType, String firstName, String lastName, String password, String email,
                                                   String preferredLanguage, String location, MultipartFile imageFile, String address, Date date_of_birth, String phone_number) ;
     ResponseEntity<String> addChauffeurWithImage(String userType, String firstName, String lastName, String password, String email,
                                                        String preferredLanguage, String location, MultipartFile imageFile, String address, Date date_of_birth, String phone_number, String numPermisConduit);

     Chauffeur SetChauffeurdispo(Long id);
     LivraisonDto getLivraisonDTOById(Long id);
    List<LivraisonDto> getAllLivraisonDTOs();
    List<LivraisonDto> getLivraisonsByUserId(Long userId);
     Contact addcontact(Contact contact);
    void removeContact(Long id_contact);
    List<Contact> retrieveContact();
    void sendContactNotification(String toEmail);
}
