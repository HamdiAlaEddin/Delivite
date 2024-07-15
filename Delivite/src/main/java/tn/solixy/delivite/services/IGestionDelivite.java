package tn.solixy.delivite.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.solixy.delivite.entities.*;

import java.util.List;
import java.util.Map;

public interface IGestionDelivite {
    User addUser(User user);
    ResponseEntity<String> addUserWithImage(User user, MultipartFile imageFile);
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
    User updateUser(User u);
    Livraison updateLivraison(Livraison l);
    Vehicule updateVehicule(Vehicule v);
    void DeleteUser(Long Uid);
    void DeleteLivraison(Long Lid);
    void DeleteVehicule(Long Vid);
  /*  public Livraison addLivraisonAndAssignToLivreur(Livraison livraison, User chauffeur);
    public User addChauffeurAndAssignToVehicule(User chauffeur, Vehicule vehicule);
  public List<Map<String, Object>> getAll() ;
     Map<String, Object> convertToMap(User us);*/
    public List<User> findByRole(Role role);
    void deleteImageFromCloudinary(String imageUrl);
    String extractImageIdFromUrl(String imageUrl);
    public void donnerNote(Long clientId, Long chauffeurId, int valeur);
}
