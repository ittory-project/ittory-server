package com.ittory.domain.member.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.member.enums.MemberStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Long socialId;

    private String name;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    private String refreshToken;


    public static Member create(Long socialId, String name, String profileImage) {
        return Member.builder()
                .socialId(socialId)
                .name(name)
                .profileImage(profileImage)
                .memberStatus(MemberStatus.ACTIVE)
                .build();
    }

    public void changeStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changeSocialId(Long socialId) {
        this.socialId = socialId;
    }

}