package com.ittory.infra.oauth.kakao.dto;

import com.ittory.infra.oauth.kakao.dto.KaKaoMemberInfo.KaKaoProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfo {

    private Long socialId;
    private String name;
    private String profileUrl;

    public static MemberInfo from(KaKaoMemberInfo kaKaoMemberInfo) {
        KaKaoProfile profile = kaKaoMemberInfo.getKakaoAccount().getProfile();

        // 기본 이미지면 null
        String profileImageUrl = profile.getProfileImageUrl();
        if (profile.getIsDefaultImage()) {
            profileImageUrl = null;
        }

        return new MemberInfo(kaKaoMemberInfo.getId(), profile.getNickname(), profileImageUrl);
    }

}
