package com.ittory.api.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginInfo {
    
    private final Long id;

    public static LoginInfo create(Long id) {
        return new LoginInfo(id);
    }

}
