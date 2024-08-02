package tn.solixy.delivite.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.solixy.delivite.entities.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IGestionDelivite {
    User addUser(String userType,User user);
    //ResponseEntity<String> addUserWithImage(String userType,User user, MultipartFile imageFile);
    public Vehicule addVehicule(Vehicule vehicule);
    public Livraison addLivraison(Livraison livraison);
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
    public Client updateClient(Client user) ;
    public Chauffeur updateChauffeur(Chauffeur chauffeur);
    public Admin updateAdmin(Admin admin);
    Livraison updateLivraison(Livraison l);
    Vehicule updateVehicule(Vehicule v);
    void DeleteUser(Long Uid);
    void DeleteLivraison(Long Lid);
    void DeleteVehicule(Long Vid);
    public List<User> findByRole(Role role);
    void deleteImageFromCloudinary(String imageUrl);
    String extractImageIdFromUrl(String imageUrl);
    public void donnerNote(Long clientId, Long chauffeurId, Rating valeur);
    /* public Optional<Chauffeur> choisirChauffeur(String location);
   public List<Vehicule> findAvailableVehicles();
    public Optional<Livraison> affecterLivraison(Long livraisonId);*/
    public BigDecimal applyDiscounts(Client user, BigDecimal deliveryPrice);
    public List<User> getAllClients(Role Client);
    public List<User> getAllRestaurants(Role Resto);
    public List<User> getAllChauffeurs(Role CHAUFFEUR);
    public List<User> getAllAdmins(Role ADMINS);
    public Chauffeur acceptChauffeur(Long id);
    public ResponseEntity<String> addUserWithImage(String userType, String firstName, String lastName, String password, String email,
                                                   String preferredLanguage, String location, MultipartFile imageFile, String address, Date date_of_birth, String phone_number) ;
    public ResponseEntity<String> addChauffeurWithImage(String userType, String firstName, String lastName, String password, String email,
                                                        String preferredLanguage, String location, MultipartFile imageFile, String address, Date date_of_birth, String phone_number, String numPermisConduit);
}
