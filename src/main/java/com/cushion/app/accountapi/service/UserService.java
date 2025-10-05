package com.cushion.app.accountapi.service;

import com.cushion.app.accountapi.config.PasswordEncoder;
import com.cushion.app.accountapi.model.AccountEntity;
import com.cushion.app.accountapi.repository.UserRepository;
import com.cushion.app.accountapi.service.exception.UserAlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountEntity register(AccountEntity accountEntity) {
        if(userRepository.findByUserName(accountEntity.getUserName()).isPresent()) {
            throw new UserAlreadyExistException("Username already exists");
        }
        accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        return userRepository.save(accountEntity);
    }

    public AccountEntity getByUsername(String username) {
        Optional<AccountEntity> optionalAccountEntity = userRepository.findByUserName(username);
        return optionalAccountEntity.orElseThrow(() -> new RuntimeException("User not found"));
    }
}
