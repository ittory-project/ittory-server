package com.ittory.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.domain.MemberWithdraw;
import com.ittory.domain.member.enums.WithdrawReason;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.member.repository.MemberWithdrawRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MemberWithdrawDomainServiceTest {

    @Autowired
    private MemberWithdrawDomainService memberWithdrawDomainService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberWithdrawRepository memberWithdrawRepository;

    @AfterEach
    void clean() {
        memberWithdrawRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("사용자 탈퇴사유를 저장한다.")
    @Test
    void saveMemberWithdrawTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member newMember = Member.create(socialId, name, profileImage);
        Member saveMember = memberRepository.save(newMember);

        //when
        memberWithdrawDomainService.saveMemberWithdraw(saveMember, WithdrawReason.ETC, "탈퇴");

        //then
        MemberWithdraw memberWithdraw = memberWithdrawRepository.findAll().get(0);
        assertThat(memberWithdraw).isNotNull();
        assertThat(memberWithdraw.getWithdrawReason()).isEqualTo(WithdrawReason.ETC);
        assertThat(memberWithdraw.getContent()).isEqualTo("탈퇴");
    }

}
