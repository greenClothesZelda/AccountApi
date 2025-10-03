package com.cushion.app.accountapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class UUIDResponseDTO {
    private UUID uuid;
}
