package com.cushion.app.accountapi.controller;

import com.cushion.app.accountapi.config.PasswordEncoder;
import com.cushion.app.accountapi.model.AccountEntity;
import com.cushion.app.accountapi.model.AccountCondition;
import com.cushion.app.accountapi.model.LoginDTO;
import com.cushion.app.accountapi.model.RegisterDTO;
import com.cushion.app.accountapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        AccountEntity accountEntity = userService.getByUsername(loginDTO.getUserName());
        if (!passwordEncoder.matches(loginDTO.getPassword(), accountEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        UUID uuid = UUID.randomUUID();
        redisTemplate.opsForHash().put(HASH_NAME, uuid.toString(), accountEntity);
        return uuid;
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        redisTemplate.opsForHash().delete(HASH_NAME, token);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterDTO registerDTO) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(registerDTO.getUserName());
        accountEntity.setPassword(registerDTO.getPassword());
        accountEntity.setEmail(registerDTO.getEmail());
        accountEntity.setAccountCondition(AccountCondition.SIGNUP);
        userService.save(accountEntity);
    }
}
