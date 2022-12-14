package com.argea.argeaportal.authorization.User;


import com.argea.argeaportal.authorization.UserFeature.UserFeature;
import com.argea.argeaportal.authorization.UserFeature.UserFeatureRepository;
import com.argea.argeaportal.rest.errorHandling.PortalOperationNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public void unlockUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new PortalOperationNotPermittedException("Unauthorized");
        }

        user.setEnabled(true);
        user.setNumberFailedLogin(0);
        userRepository.save(user);
    }

/*
    private final String USER_UNKNOWN = "Utente Sistema";


    @Autowired
    UserFeatureRepository userFeatureRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public PortalConfigurationInterface portalConfigurationInterface;



    public Locale getMyLocale(){
        return Locale.ITALY;//implementare impostazione locale da db se necessario
    }

    public String getMyUsername() {
        return portalConfigurationInterface.getUsername();
    }


    public String getMyEmail() {
        return userExtService.getUserExt(portalConfigurationInterface.getUsername()).getEmail();
    }


    public List<UserExt> getUsersByFeature(String feature) {

        List<UserFeature> userFeatures = userFeatureRepository.findAllByFeatureAndEnabled(feature, true);
        List<UserExt> userExts = new ArrayList<>();
        for (UserFeature userFeature : userFeatures) {
            UserExt userExt = userExtService.getUserExt(userFeature.getUsername());
            userExts.add(userExt);
        }
        return userExts;
    }

    public List<String> getUsernamesByFeature(String feature) {
        List<UserFeature> userFeatures = userFeatureRepository.findAllByFeatureAndEnabled(feature, true);
        return userFeatures.stream().map(UserFeature::getUsername).collect(Collectors.toList());
    }

    public String getNomeCognomeFromUsername(String username) {
        UserExt userExt = userExtService.getUserExt(username);
        if (userExt != null) {
            return userExt.getNome() + " " + userExt.getCognome();
        } else {
            return USER_UNKNOWN;
        }
    }

    public String getNomeCognome(UserExt userExt) {
        if (userExt != null) {
            return userExt.getNome() + " " + userExt.getCognome();
        } else {
            return USER_UNKNOWN;
        }
    }

    public String getMioNome() {
        UserExt userExt = userExtService.getUserExt(portalConfigurationInterface.getUsername());
        if (userExt != null) {
            return userExt.getNome() + " " + userExt.getCognome();
        } else {
            return USER_UNKNOWN;
        }
    }

    public String getMioNomeSystemUserSafe() {
        if (portalConfigurationInterface.getUsername().equals("")) {
            return "System";
        }
        return getMioNome();
    }


    public void unlockUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new PortalOperationNotPermittedException("Unauthorized");
        }

        user.setEnabled(true);
        user.setNumberFailedLogin(0);
        userRepository.save(user);
        return;
    }

    //occhio che non è cacheata nemmeno in session
    public boolean doIHaveFeatureEnabled(String feature) {
        List<UserFeature> userFeatures = userFeatureRepository.findAllByUsernameAndFeature(this.getMyUsername(), feature);
        if (userFeatures != null && userFeatures.size() == 1) {
            return userFeatures.get(0).getEnabled();
        } else {
            return false;
        }
    }
    */

}
