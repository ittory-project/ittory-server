package com.ittory.infra.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KaKaoTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}
