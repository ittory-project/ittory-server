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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String name;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;


    public static Member toEntity(String email, String name, String profileImage) {
        return Member.builder()
                .email(email)
                .name(name)
                .profileImage(profileImage)
                .memberStatus(MemberStatus.ACTIVE)
                .build();
    }

    public void changeStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

}