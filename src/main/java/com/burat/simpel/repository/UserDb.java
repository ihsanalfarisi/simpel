package com.burat.simpel.repository;

import com.burat.simpel.model.TitleModel;
import com.burat.simpel.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDb extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);
    @Query("SELECT DISTINCT a.username FROM UserModel a")
    List<String> findUsernameUser();
}
