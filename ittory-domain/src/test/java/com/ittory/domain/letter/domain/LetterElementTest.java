package com.ittory.domain.letter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LetterElementTest {


    @DisplayName("내용 변경 기능 테스트")
    @Test
    void changeContentTest() {
        // given
        LetterElement element = LetterElement.create(null, null, null, 0, null);

        // when
        element.changeContent("test content");

        // then
        assertThat(element.getContent()).isEqualTo("test content");
    }

    @DisplayName("작성자 변경 기능 테스트")
    @Test
    void changeMemberTest() {
        // given
        LetterElement element = LetterElement.create(null, null, null, 0, null);
        Member member = Member.create(1L, "member", "image");

        // when
        element.changeMember(member);

        // then
        assertThat(element.getMember().getName()).isEqualTo("member");
    }

}
