package com.argea.argeaportal.authorization.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findFirstByUsername(String username);

    User findByUsername(String username);

    List<User> findAllByEnabled(Boolean enabled);

    List<User> findAll();
}
