package com.argea.argeaportal.authorization.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserSimpleRepository extends CrudRepository<UserSimple, String> {

    List<UserSimple> findFirstByUsername(String username);

    UserSimple findByUsername(String username);

    List<UserSimple> findAllByEnabled(Boolean enabled);

    List<UserSimple> findAll();
}
