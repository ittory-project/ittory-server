package com.ittory.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KaKaoLoginRequest {

    @Schema(description = "==삭제 예정== 카카오 엑세스 토큰")
    @Nullable
    private String accessToken;

    @Schema(description = "카카오 인가 코드")
    @Nullable
    private String authorizationCode;

}