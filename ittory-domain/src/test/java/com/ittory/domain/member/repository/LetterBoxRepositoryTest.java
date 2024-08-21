package com.ittory.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.LetterBoxType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LetterBoxRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private LetterBoxRepository letterBoxRepository;

    @AfterEach
    void clean() {
        letterBoxRepository.deleteAll();
        letterRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @DisplayName("보관 상태의 편지가 있는지 확인한다.")
    @Test
    void existsInLetterBoxTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member member = memberRepository.save(Member.create(socialId, name, profileImage));
        Letter newLetter = Letter.create(null, null, "receiver", null, "title", null);
        Letter letter = letterRepository.save(newLetter);
        letterBoxRepository.save(LetterBox.create(member, letter, LetterBoxType.RECEIVE));

        //when
        Boolean isStored = letterBoxRepository.existsInLetterBox(letter.getId());

        //then
        assertThat(isStored).isTrue();
    }

}
