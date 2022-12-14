package com.argea.argeaportal.authorization.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AuthorityRepository extends CrudRepository<Authority, String> {

    List<Authority> findAllByAuthorityId_User_Username(String username);

}
