package com.ittory.domain.letter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.participant.domain.Participant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ElementTest {


    @DisplayName("내용 변경 기능 테스트")
    @Test
    void changeContentTest() {
        // given
        Element element = com.ittory.domain.letter.domain.Element.create(null, null, null, 0, null);

        // when
        element.changeContent("test content");

        // then
        assertThat(element.getContent()).isEqualTo("test content");
    }

    @DisplayName("작성자 변경 기능 테스트")
    @Test
    void changeMemberTest() {
        // given
        Element element = com.ittory.domain.letter.domain.Element.create(null, null, null, 0, null);
        Member member = Member.create(1L, "member", "image");
        Participant participant = Participant.create(member, null, "participant");

        // when
        element.changeParticipant(participant);

        // then
        assertThat(element.getParticipant().getNickname()).isEqualTo("participant");
    }

}
