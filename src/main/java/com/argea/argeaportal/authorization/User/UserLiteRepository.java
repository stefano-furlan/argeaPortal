package com.argea.argeaportal.authorization.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLiteRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {


    //VUOLE per forza gli As o non funziona, anche se il nome è uguale.... che simpatico
    @Query("select us.username as username, us.enabled as enabled,us.blocked as blocked, us.numberFailedLogin as numberFailedLogin,us.lastLogin as lastLogin, us.nome as nome, us.cognome as cognome from User us ")
    List<UserLite> getUserLiteAll();
}