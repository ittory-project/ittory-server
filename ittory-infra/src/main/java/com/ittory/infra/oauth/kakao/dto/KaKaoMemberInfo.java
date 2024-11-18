package com.ittory.infra.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KaKaoMemberInfo {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("kakao_account")
    private KaKaoAccount kakaoAccount;

    @Getter
    public static class KaKaoAccount {
        @JsonProperty("profile")
        private KaKaoProfile profile;
    }

    @Getter
    public static class KaKaoProfile {
        @JsonProperty("nickname")
        private String nickname;
        @JsonProperty("thumbnail_image_url")
        private String thumbnailImageUrl;
        @JsonProperty("profile_image_url")
        private String profileImageUrl;
        @JsonProperty("is_default_image")
        private Boolean isDefaultImage;
    }

}
