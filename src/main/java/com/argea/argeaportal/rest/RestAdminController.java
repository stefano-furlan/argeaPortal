package com.argea.argeaportal.rest;


import com.argea.argeaportal.authorization.AuthorizationDataService;
import com.argea.argeaportal.authorization.Feature.Feature;
import com.argea.argeaportal.authorization.Feature.FeatureRepository;
import com.argea.argeaportal.authorization.User.*;
import com.argea.argeaportal.authorization.UserFeature.UserFeature;
import com.argea.argeaportal.authorization.UserFeature.UserFeatureRepository;
import com.argea.argeaportal.authorization.dto.UserDto;
import com.argea.argeaportal.authorization.dto.UserDtoCreation;
import com.argea.argeaportal.authorization.dto.UserFeatureDto;
import com.argea.argeaportal.authorization.dto.UserPasswordDto;
import com.argea.argeaportal.rest.errorHandling.PortalOperationNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rs/admin")
class RestAdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserLiteRepository userLiteRepository;

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    UserFeatureRepository userFeatureRepository;

    @Autowired
    AuthorizationDataService authorizationDataService;




    @GetMapping("/user")
    public UserDto getUtente(String username) {
        List<User> users = userRepository.findFirstByUsername(username);
        if (users.size() != 1) {
            throw new PortalOperationNotPermittedException("Errore");
        }
        User utente = users.get(0);
        if (utente.getUsername() == null || utente.getUsername().trim().equals("")) {
            throw new PortalOperationNotPermittedException("Errore");
        }
        return authorizationDataService.convertToDto(users.get(0));
    }

    @PutMapping("/user")
    @ResponseBody
    public void salvaUtente(@RequestBody UserDto utente) {
        authorizationDataService.aggiornaUtente(utente);
    }

    @DeleteMapping("/user")
    @ResponseBody
    public void eliminaUtente(@RequestBody UserDto utente) {
        authorizationDataService.eliminaUtente(utente);
    }

    @PostMapping("/user")
    @ResponseBody
    public void creaUtente(@RequestBody UserDtoCreation utente) {
        authorizationDataService.creaUtente(utente);
    }

    @PutMapping("/change-password")
    @ResponseBody
    public void modificaPassword(@RequestBody UserPasswordDto utente) {

        List<User> users = userRepository.findFirstByUsername(utente.getUsername());
        //salvo l'utente
        if (users.size() != 1) {
            throw new PortalOperationNotPermittedException("Impossibile portare a termine l'operazione");
        }
        //gestisci la password
        User user = users.get(0);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPassword = passwordEncoder.encode(utente.getPassword());
        user.setPassword(newPassword);
        if (utente.getMustChangePassword() == null) {
            user.setMustChange(true);
        } else {
            user.setMustChange(utente.getMustChangePassword());
        }
        user.setLastChangePassword(LocalDateTime.now());

        userRepository.save(user);

    }

    @PostMapping("/invalidate-caches")
    public void invalidateCaches() {
       /* userExtService.invalidateUserExtCache();
        sambaImmagineArticoloCacheUrlService.invalidate();*/
        authorizationDataService.invalidateCache();
/*        workflowServiceCache.invalidate();
        checklistCacheService.invalidateCache();
        ricevimentiServiceCache.invalidateCache();
        keyValueInfoCachedService.invalidateCache();*/
    }


    @PutMapping("/user/feature")
    @ResponseBody
    public UserFeatureDto setUserFeature(@RequestBody UserFeatureDto userFeatureDto) {
        return authorizationDataService.setUserFeature(userFeatureDto);
    }



    @PostMapping("/user/unlock")
    public void unlock(@RequestBody String user) {
        userService.unlockUser(user);
    }

    @GetMapping("/users")
    public List<UserLite> users() {
        return userLiteRepository.getUserLiteAll();
    }

    @GetMapping("/features")
    public List<Feature> features() {
        return featureRepository.findAllByHiddenIsFalseOrHiddenIsNull();
    }

    @GetMapping("users/features")
    public List<UserFeature> userFeatures() {
        return userFeatureRepository.findAll();
    }




    @GetMapping("/test")
    public String email() {

      /*  try {
            as400Repository.rebuildTabellaPrimeProduzioniAnalisiVisivaAFiniMarketing();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return "ok";
    }


}
