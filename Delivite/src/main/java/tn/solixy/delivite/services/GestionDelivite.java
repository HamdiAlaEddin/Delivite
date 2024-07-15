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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
   /* @Override
     public List<Map<String, Object>> getAll() {
            List<User> users = iUserRepository.findAll();
            return users.stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
        }
    @Override
        public Map<String, Object> convertToMap(User us) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", us.getUserID());
            userMap.put("email", us.getEmail());
            userMap.put("firstName", us.getFirstName());
            userMap.put("lastName", us.getLastName());
            userMap.put("role", us.getRole());


            if (us.getRole().getRole() == RoleName.Chauffeur) {
                userMap.put("drivingLicenseNumber", us.getNumPermisConduit());
                userMap.put("rate", us.getRate());
                userMap.put("vehicle", us.getVehicule());
            } else {
                userMap.put("drivingLicenseNumber", "");
                userMap.put("rate", 0);
                userMap.put("vehicle", null);
            }

            return userMap;
        }
*/
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
        iUserRepository.deleteById(Uid);
    }
    @Override
    public void DeleteLivraison(Long Lid) {
        iLivraisonRepository.deleteById(Lid);
    }
    @Override
    public void DeleteVehicule(Long Vid) {
        Vehicule vehicule = iVehiculeRepository.findById(Vid).orElse(null);
        if (vehicule != null) {
            for (Chauffeur a : vehicule.getChauffeurs()) {a.setVehicula(null);iUserRepository.save(a);}
            iVehiculeRepository.deleteById(Vid);}}
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
        // extract imageID from URL /blablabla
        return imageUrl.substring(lastSlashIndex + 1, lastDotIndex);
    }
    @Override
    public User addUser(User user) {
        LocalDate currentDate = LocalDate.now();
        Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setRegistrationDate(date);
        return iUserRepository.save(user);
    }
    public ResponseEntity<String> addUserWithImage(User user, MultipartFile imageFile) {

                // Enregistrer l'image sur Cloudinary
        Map uploadResult = null;
        try {
            uploadResult = cloudinaryService.upload(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Récupérer l'URL de l'image depuis Cloudinary
                String imageUrl = (String) uploadResult.get("url");
                Image image = new Image();
                image.setName(imageFile.getOriginalFilename());
                image.setImageURL(imageUrl);
                imageRepository.save(image);
                user.setImage(image);
                LocalDate currentDate = LocalDate.now();
                Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                user.setRegistrationDate(date);
                iUserRepository.save(user);
                return new ResponseEntity<>("User added successfully", HttpStatus.OK);
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
    /*  public User addChauffeurAndAssignToVehicule(User chauffeur, Vehicule vehicule) {
        chauffeur.setVehicule_id(vehicule.getVehiculeID());
         return iUserRepository.save(chauffeur);
     }
     public Livraison addLivraisonAndAssignToLivreur(Livraison livraison, User chauffeur) {
         livraison.setChauffeur(chauffeur);
         return iLivraisonRepository.save(livraison);
     }*/
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
    public void donnerNote(Long clientId, Long chauffeurId, int valeur) {
        Client client = (Client) iUserRepository.findById(clientId).orElse(null);
        Chauffeur chauffeur = (Chauffeur) iUserRepository.findById(chauffeurId).orElse(null);

        if (client != null && chauffeur != null) {
            Note note = new Note();
            note.setClient(client);
            note.setChauffeur(chauffeur);
            note.setValeur(valeur);

            chauffeur.addNote(note);
            iUserRepository.save(chauffeur);
        }
    }
}
