package com.ittory.domain.letter.domain;

import com.ittory.domain.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LetterTest {

    @DisplayName("편지의 받는 사람 변경 기능 테스트")
    @Test
    void changeLetterReceiverTest() {
        // given
        Letter letter = Letter.create(null, null, null, null, "test-letter", null);
        String memberName = "test member";
        Member member = Member.create("test@email.com", memberName, "http://member.Image");

        // when
        letter.changeReceiver(member);

        // then
        Assertions.assertThat(letter.getReceiver().getName()).isEqualTo(memberName);
    }

}
