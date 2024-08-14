package com.ittory.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.exception.MemberException.MemberNotFoundException;
import com.ittory.domain.member.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MemberDomainServiceTest {

    @Autowired
    private MemberDomainService memberDomainService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @DisplayName("멤버를 저장할 수 있다.")
    @Test
    void saveMemberTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";

        //when
        memberDomainService.saveMember(socialId, name, profileImage);

        //then
        List<Member> allMembers = memberRepository.findAll();
        assertThat(allMembers.size()).isEqualTo(1);
        assertThat(allMembers.get(0).getName()).isEqualTo(name);
    }

    @DisplayName("멤버 아이디로 멤버를 조회할 수 있다.")
    @Test
    void findByMemberIdTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member newMember = Member.create(socialId, name, profileImage);
        Member save = memberRepository.save(newMember);

        //when
        Member member = memberDomainService.findMemberById(save.getId());

        //then
        assertThat(member.getName()).isEqualTo(name);
    }

    @DisplayName("조회하는 멤버가 없다면 예외가 발생한다.")
    @Test
    void findByMemberIdTest_MemberNotFoundException() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member newMember = Member.create(socialId, name, profileImage);
        memberRepository.save(newMember);

        //when & then
        assertThatThrownBy(() -> memberDomainService.findMemberById(2L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("소셜 아이디로 멤버를 조회할 수 있다.")
    @Test
    void findBySocialIdTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member newMember = Member.create(socialId, name, profileImage);
        memberRepository.save(newMember);

        //when
        Member member = memberDomainService.findMemberBySocialId(socialId);

        //then
        assertThat(member.getSocialId()).isEqualTo(socialId);
        assertThat(member.getName()).isEqualTo(name);
    }

    @DisplayName("소셜 아이디가 일치하는 멤버가 없다면 Null을 반환한다.")
    @Test
    void findBySocialIdTest_Null() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member newMember = Member.create(socialId, name, profileImage);
        memberRepository.save(newMember);

        //when
        Member member = memberDomainService.findMemberBySocialId(2L);

        //then
        assertThat(member).isNull();
    }

    @DisplayName("리프레시 토큰을변경 할 수 있다..")
    @Test
    void changeRefreshTokenTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member newMember = Member.create(socialId, name, profileImage);
        memberRepository.save(newMember);

        String refreshToken = "member.token.refresh";

        //when
        memberDomainService.changeRefreshToken(newMember, refreshToken);

        //then
        Member member = memberRepository.findById(newMember.getId()).orElse(null);
        assertThat(member).isNotNull();
        assertThat(member.getRefreshToken()).isEqualTo(refreshToken);
    }

}
