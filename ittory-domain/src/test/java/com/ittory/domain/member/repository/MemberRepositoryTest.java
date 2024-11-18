package com.ittory.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.member.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }


    @DisplayName("소셜 아이디로 사용자를 조회할 수 있다.")
    @Test
    void findBySocialIdTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        memberRepository.save(Member.create(socialId, name, profileImage));

        //when
        Member member = memberRepository.findBySocialId(socialId).orElse(null);

        //then
        assertThat(member).isNotNull();
        assertThat(member.getSocialId()).isEqualTo(socialId);
        assertThat(member.getName()).isEqualTo(name);

    }

}
