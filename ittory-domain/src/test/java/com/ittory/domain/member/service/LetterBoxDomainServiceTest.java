package com.ittory.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.LetterBoxType;
import com.ittory.domain.member.repository.LetterBoxRepository;
import com.ittory.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LetterBoxDomainServiceTest {

    @Autowired
    private LetterBoxDomainService letterBoxDomainService;

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

    @DisplayName("멤버를 저장할 수 있다.")
    @Test
    void saveMemberTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member member = memberRepository.save(Member.create(socialId, name, profileImage));
        Letter newLetter = Letter.create(null, null, "receiver", null, "title", null);
        Letter letter = letterRepository.save(newLetter);
        letterBoxRepository.save(LetterBox.create(member, letter, LetterBoxType.RECEIVE));

        //when
        Boolean isStored = letterBoxDomainService.checkStorageStatus(letter.getId());

        //then
        assertThat(isStored).isTrue();
    }

}
