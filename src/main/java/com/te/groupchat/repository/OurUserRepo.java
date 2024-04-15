package com.te.groupchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.te.groupchat.model.OurUser;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<OurUser, Integer> {
    @Query(value = "select * from ourusers where email = ?1", nativeQuery = true)
    Optional<OurUser> findByEmail(String email);
}
