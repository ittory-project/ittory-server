package com.ittory.domain.member.domain;

import com.ittory.domain.member.enums.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberTest {

    @DisplayName("멤버의 상태를 변경 기능 테스트")
    @Test
    void changeMemberStatusTest() {
        // given
        Member member = Member.create(1L, "test member", "http://member.Image");

        // when
        member.changeStatus(MemberStatus.BANDED);

        // then
        Assertions.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.BANDED);
    }

    @DisplayName("멤버의 리프레쉬 토큰을 변경 기능 테스트")
    @Test
    void changeMemberRefreshTokenTest() {
        // given
        Member member = Member.create(1L, "test member", "http://member.Image");
        String refreshToken = "changed.refresh.token";

        // when
        member.changeRefreshToken(refreshToken);

        // then
        Assertions.assertThat(member.getRefreshToken()).isEqualTo(refreshToken);
    }

}
