package com.ittory.infra.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KaKaoTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}
