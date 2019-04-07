package com.example.springbootexamples.repository;

import com.example.springbootexamples.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CustomizedRepoistory<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.userName=:username")
    User findUser(@Param("username") String userName);
}
