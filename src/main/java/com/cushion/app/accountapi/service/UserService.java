package com.cushion.app.accountapi.service;

import com.cushion.app.accountapi.config.PasswordEncoder;
import com.cushion.app.accountapi.model.AccountEntity;
import com.cushion.app.accountapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountEntity save(AccountEntity accountEntity) {
        accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        return userRepository.save(accountEntity);
    }

    public AccountEntity getByUsername(String username) {
        Optional<AccountEntity> optionalAccountEntity = userRepository.findByUserName(username);
        return optionalAccountEntity.orElseThrow(() -> new RuntimeException("User not found"));
    }
}
