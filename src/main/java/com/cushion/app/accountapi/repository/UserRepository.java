package com.cushion.app.accountapi.repository;

import com.cushion.app.accountapi.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AccountEntity, String> {
    Optional<AccountEntity> findByUserName(String userName); }
