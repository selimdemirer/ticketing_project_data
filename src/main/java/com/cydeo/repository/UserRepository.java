package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String username); // derived query

    @Transactional
    void deleteByUserName(String username); // derived query


}
