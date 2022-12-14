package com.argea.argeaportal.authorization;

import com.argea.argeaportal.authorization.Feature.Feature;
import com.argea.argeaportal.authorization.Feature.FeatureRepository;
import com.argea.argeaportal.authorization.User.Authority;
import com.argea.argeaportal.authorization.User.AuthorityRepository;
import com.argea.argeaportal.authorization.User.User;
import com.argea.argeaportal.authorization.User.UserRepository;
import com.argea.argeaportal.authorization.UserFeature.UserFeature;
import com.argea.argeaportal.authorization.UserFeature.UserFeatureRepository;
import com.argea.argeaportal.authorization.dto.UserDto;

import com.argea.argeaportal.authorization.dto.UserDtoCreation;
import com.argea.argeaportal.authorization.dto.UserFeatureDto;
import com.argea.argeaportal.authorization.dto.UserInfoLite;
import com.argea.argeaportal.dto.ClienteArgeaDto;
import com.argea.argeaportal.rest.errorHandling.PortalRecoverableErrorException;
import com.argea.argeaportal.rest.errorHandling.PortalSecurityException;
import com.argea.argeaportal.utils.StringUtils;
import org.hibernate.transform.Transformers;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorizationDataService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserFeatureRepository userFeatureRepository;

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    public UserDto convertToDto(User user) {
      /*   UserDto userDto = new UserDto();


       userDto.setUsername(user.getUsername());
        userDto.setEnabled(user.getEnabled());
        userDto.setMustChangePassword(user.getMustChange());
        userDto.setBlocked(user.getBlocked());
        userDto.setSsoEnabled(user.getSsoEnabled());

        userDto.setSsoUser(user.getUserExt().getUtenteSso());
        userDto.setCognome(user.getUserExt().getCognome());
        userDto.setNome(user.getUserExt().getNome());
        userDto.setEmail(user.getUserExt().getEmail());
        userDto.setCodiceFornitore(user.getUserExt().getCodiceFornitore());
        userDto.setDescrizioneAzienda(user.getUserExt().getDescrizioneAzienda());
        userDto.setUtenteGalileo(user.getUserExt().getUtenteGalileo());

        userDto.setNote(user.getUserExt().getNote());*/

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setPreferNestedProperties(false);

        UserDto userDto = modelMapper.map(user, UserDto.class);

        return userDto;
    }

    public User mergeToEntity(UserDto userDto, User user) {

        if (userDto.getUsername() == null) {
            throw new PortalRecoverableErrorException("Inserire l'username");
        }

        if (user.getUsername() != null && !user.getUsername().equalsIgnoreCase(userDto.getUsername())) {
            throw new PortalRecoverableErrorException("Impossibile cambiare l'username di un utente già creato");
        }

        user.setUsername(userDto.getUsername());


        user.setEnabled(userDto.getEnabled());
        if (userDto.getMustChangePassword() == null) {
            user.setMustChange(false);
        } else {
            user.setMustChange(userDto.getMustChangePassword());
        }

        user.setBlocked(userDto.getBlocked());
        user.setSsoEnabled(userDto.getSsoEnabled());

        user.setSsoUser(userDto.getSsoUser());
        user.setCognome(userDto.getCognome());
        user.setNome(userDto.getNome());
        user.setEmail(userDto.getEmail());
        user.setNote(userDto.getNote());


        if (user.getPassword() == null || user.getPassword().equalsIgnoreCase("")) {
            user.setPassword("NULL");//non è un hash valido quindi non accede
        }


        return user;
    }


    public void eliminaUtente(UserDto utente) {
        List<User> users = userRepository.findFirstByUsername(utente.getUsername());
        //salvo l'utente
        if (users.size() == 0) {
            throw new PortalRecoverableErrorException("Impossibile eliminare l'utente");
        }
        if (utente.getUsername() == null || utente.getUsername().trim().equals("")) {
            throw new PortalRecoverableErrorException("Impossibile eliminare un utente con username vuota");
        }
        User user = users.get(0);

        //elimino le authorities
        //TODO: implementare delete utente
        //elimino le userFeatures

        //elimino l'utente
        userRepository.delete(user);

    }


    public UserFeatureDto setUserFeature(UserFeatureDto userFeatureDto) {
        List<User> users = userRepository.findFirstByUsername(userFeatureDto.getUsername());
        if (users.size() == 0) {
            throw new PortalSecurityException("Utente inesistente");
        }
        if (userFeatureDto.getUsername() == null || userFeatureDto.getUsername().trim().equals("")) {
            throw new PortalRecoverableErrorException("username non specificata");
        }

        Optional<Feature> feature = featureRepository.findById(userFeatureDto.getFeature());
        if (feature.isEmpty()) {
            throw new PortalSecurityException("Feature inesistente");
        }

        User user = users.get(0);

        List<UserFeature> userFeatures = userFeatureRepository.findAllByUsernameAndFeature(userFeatureDto.getUsername(), userFeatureDto.getFeature());

        if (userFeatures.size() == 0) {
            UserFeature userFeature = new UserFeature();
            userFeature.setUsername(userFeatureDto.getUsername());
            userFeature.setFeature(userFeatureDto.getFeature());
            userFeature.setEnabled(userFeatureDto.getValue());
            userFeatureRepository.save(userFeature);
        } else {
            UserFeature userFeature = userFeatures.get(0);
            userFeature.setEnabled(userFeatureDto.getValue());
            userFeatureRepository.save(userFeature);
        }

        //rebuild authorities: vanno aggiunte alle authorities tutte quelle richieste
        String strRequiredAuthorities = feature.get().getRequiredAuthorities();

        List<String> requiredAuthorities = new ArrayList<>();
        if (strRequiredAuthorities != null) {
            requiredAuthorities = Arrays.asList(strRequiredAuthorities.split("[,]", 0));
        }

        if (userFeatureDto.getValue() == true) {
            for (String authority : requiredAuthorities) {
                String authorityRealName = "ROLE_" + authority;//DIO STRAMALEDICA QUELLI CHE HANNO FATTO SPRING co sto cavolo di prefisso
                List<Authority> filteredAuthorities = user.getAuthorities().stream().filter(f -> authorityRealName.equalsIgnoreCase(f.getAuthorityId().getAuthority())).collect(Collectors.toList());
                if (filteredAuthorities.size() == 0) {
                    Authority authority1 = new Authority();
                    authority1.setAuthorityId(new Authority.Pk());
                    authority1.getAuthorityId().setUser(user);
                    authority1.getAuthorityId().setAuthority(authorityRealName);
                    user.getAuthorities().add(authority1);
                }
            }
            userRepository.save(user);
        } else {
            //rimozione di authorities se non servono più!!!!
            //ciclo le features del cliente

            List<UserFeature> userFeaturesUtente = userFeatureRepository.findAllByUsernameAndEnabled(user.getUsername(), true);
            List<Feature> features = featureRepository.findAll();

            List<String> usedAuthorities = new ArrayList<>();
            for (UserFeature f : userFeaturesUtente) {
                if (!userFeatureDto.getFeature().equalsIgnoreCase(f.getFeature())) {//escludo quella che sto disabilitando!
                    Feature featureAnalyzed = features.stream().filter(v -> v.getId().equalsIgnoreCase(f.getFeature())).findAny().orElse(null);
                    if (featureAnalyzed != null && featureAnalyzed.getRequiredAuthorities() != null) {
                        List<String> requiredAuthoritiesForFeature = Arrays.asList(featureAnalyzed.getRequiredAuthorities().split("[,]", 0));
                        for (String s : requiredAuthoritiesForFeature) {
                            usedAuthorities.add("ROLE_" + s);
                        }
                    }
                }
            }
            usedAuthorities.add("ROLE_USER");

            //a questo punto usedAuthorities ha tutte le authorities che servono per tutte le features abilitate dell'utente
            //elimino tutte le authorities che NON sono nell'array
            Iterator<Authority> iterator = user.getAuthorities().iterator();
            while (iterator.hasNext()) {
                String a = iterator.next().getAuthorityId().getAuthority();
                if (!usedAuthorities.contains(a)) {
                    iterator.remove();
                }
            }
            userRepository.save(user);
        }

        UserFeatureDto userFeatureDtoOut = new UserFeatureDto();
        userFeatureDtoOut.setValue(userFeatureDto.getValue());
        return userFeatureDtoOut;
    }

    public void creaUtente(UserDtoCreation utente) {
        List<User> users = userRepository.findFirstByUsername(utente.getUsername().trim());
        //salvo l'utente
        if (users.size() != 0) {
            throw new PortalRecoverableErrorException("Impossibile creare l'utente");
        }
        if (utente.getUsername() == null || utente.getUsername().trim().equals("")) {
            throw new PortalRecoverableErrorException("username non specificata");
        }
        //trimmo e pulisco
        utente.setUsername(StringUtils.removeNonPrintableCharachters(utente.getUsername().trim()));
        utente.setNewPassword(StringUtils.removeNonPrintableCharachters(utente.getNewPassword().trim()));
        //gestisci la password
        User user = new User();

        user = mergeToEntity(utente, user);

        if (utente.getNewPassword() != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPassword = passwordEncoder.encode(utente.getNewPassword());
            user.setPassword(newPassword);
        } else {
            user.setPassword("NULL");
        }
        user.setLastChangePassword(LocalDateTime.now());
        if (user.getSsoEnabled() == null) {
            user.setSsoEnabled(false);
        }
        if (user.getBlocked() == null) {
            user.setBlocked(false);
        }

        //aggiungo il ruolo user
        Authority authority = new Authority();
        authority.setAuthorityId(new Authority.Pk());
        authority.getAuthorityId().setUser(user);
        authority.getAuthorityId().setAuthority("ROLE_USER");
        user.getAuthorities().add(authority);

        userRepository.save(user);
    }


    public void aggiornaUtente(UserDto utente) {
        //controlli preliminari
        List<User> users = userRepository.findFirstByUsername(utente.getUsername());
        if (users.size() != 1) {
            throw new PortalSecurityException("Errore, tentativo di salvare utente inesistente:" + utente.getUsername());
        }
        if (utente.getUsername() == null || utente.getUsername().trim().equalsIgnoreCase("")) {
            throw new PortalRecoverableErrorException("Username vuoto");
        }

        User user = users.get(0);
        user = this.mergeToEntity(utente, user);

        userRepository.save(user);
    }

    @Cacheable(value = "UtentiByFeature", key = "#feature")
    public List<UserInfoLite> getUtentiByFeature(String feature) {
        //FIXME: usare query hibernate

        List<UserInfoLite> items = entityManager
                .createNativeQuery(
                        "select u.username, e.nome, e.cognome, e.nome +' '+e.cognome as fullname  " +
                                " from user_feature f " +
                                " inner join users u " +
                                " on f.username=u.username " +
                                " inner join user_ext e " +
                                " on u.username=e.username " +
                                " where u.enabled=1 and f.enabled=1 and feature='" + feature + "'")
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(Transformers.aliasToBean(UserInfoLite.class)) //TODO: al momento non esite una soluzione alternativa (hibernate6 è in fase di definizione (pare)
                .getResultList();
        items.sort(Comparator.comparing(UserInfoLite::getNome).thenComparing(UserInfoLite::getCognome));
        return items;

    }

    @CacheEvict(value = "UtentiByFeature", allEntries = true)
    public void invalidateCache() {
        return;
    }
}
