package com.kallam.api.security.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kallam.api.security.user.model.User;


public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);

}
