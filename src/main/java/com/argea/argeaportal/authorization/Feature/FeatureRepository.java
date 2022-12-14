package com.argea.argeaportal.authorization.Feature;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeatureRepository extends CrudRepository<Feature, String> {

  List<Feature> findAllByHiddenIsFalseOrHiddenIsNull();
  List<Feature> findAll();
  Feature findFeatureById(String id);


}
