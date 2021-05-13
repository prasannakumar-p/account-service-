package com.osg.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osg.accountservice.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findUserByUsername(String username);

	User findUserByUsernameAndPassword(String username, String password);

	User findUserByToken(String token);

}
