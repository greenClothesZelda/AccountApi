package com.cushion.app.accountapi.model;

import com.cushion.app.accountapi.model.exceptions.RoleNotDefineException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN, MEMBER;
    @JsonCreator
    public static Role fromString(String str){
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(str)) {
                return role;
            }
        }
        throw new RoleNotDefineException();
    }

    @JsonValue
    public String toJson(){
        return this.name().toLowerCase();
    }

}
