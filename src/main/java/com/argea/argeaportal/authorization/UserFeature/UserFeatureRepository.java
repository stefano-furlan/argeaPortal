package com.argea.argeaportal.authorization.UserFeature;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFeatureRepository extends CrudRepository<UserFeature, Integer> {

    List<UserFeature> findAllByUsernameAndEnabled(String username,Boolean enabled);
    List<UserFeature> findAllByUsername(String username);
    List<UserFeature> findAllByUsernameAndFeature(String username,String feature);
    List<UserFeature> findAllByFeatureAndEnabled(String feature,Boolean enabled);
    List<UserFeature> findAll();


}
