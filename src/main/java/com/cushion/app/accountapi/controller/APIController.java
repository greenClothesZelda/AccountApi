package com.cushion.app.accountapi.controller;

import com.cushion.app.accountapi.config.PasswordEncoder;
import com.cushion.app.accountapi.controller.exception.LoginFailException;
import com.cushion.app.accountapi.model.AccountEntity;
import com.cushion.app.accountapi.model.AccountCondition;
import com.cushion.app.accountapi.model.LoginDTO;
import com.cushion.app.accountapi.model.RegisterDTO;
import com.cushion.app.accountapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
@ResponseBody
public class APIController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper redisMapper;
    private final String HASH_NAME = "SESSION:";

    @PostMapping("/login")
    public UUID login(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("Login attempt for user: {}", loginDTO.getUserName());
        AccountEntity accountEntity = userService.getByUsername(loginDTO.getUserName());
        if (!passwordEncoder.matches(loginDTO.getPassword(), accountEntity.getPassword())) {
            throw new LoginFailException("Invalid password");
        }
        UUID uuid = UUID.randomUUID();
        try {
            redisTemplate.opsForHash().put(HASH_NAME, uuid.toString(), accountEntity);
        } catch (Exception e){
            log.error("Redis error: {}", e.getMessage());
            throw new LoginFailException();
        }
        return uuid;
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        redisTemplate.opsForHash().delete(HASH_NAME, token);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterDTO registerDTO) {
        log.info("Register attempt for user: {}", registerDTO.getUserName());
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(registerDTO.getUserName());
        accountEntity.setPassword(registerDTO.getPassword());
        accountEntity.setEmail(registerDTO.getEmail());
        accountEntity.setAccountCondition(AccountCondition.SIGNUP);
        userService.register(accountEntity);
    }
}
