package com.ittory.domain.letter.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.letter.domain.LetterImage;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import com.ittory.domain.letter.repository.FontRepository;
import com.ittory.domain.letter.repository.LetterElementRepository;
import com.ittory.domain.letter.repository.LetterImageRepository;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.member.repository.ParticipantRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LetterElementDomainServiceTest {

    @Autowired
    private LetterElementDomainService letterElementDomainService;

    @Autowired
    private LetterElementRepository letterElementRepository;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private CoverTypeRepository coverTypeRepository;

    @Autowired
    private FontRepository fontRepository;

    @Autowired
    private LetterImageRepository letterImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipantRepository participantRepository;


    @AfterEach
    void clean() {
        letterElementRepository.deleteAll();
        participantRepository.deleteAll();
        letterRepository.deleteAll();
        coverTypeRepository.deleteAll();
        fontRepository.deleteAll();
        letterImageRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void changeContentTest() {
        //given
        Member member = Member.create(1L, "member", "image");
        CoverType coverType = CoverType.create("type", "image");
        Font font = Font.create("font");
        LetterImage letterImage = LetterImage.create("image");

        Letter letter = Letter.create(coverType, font, "receiver", LocalDateTime.now(), "title", "image");
        LetterElement element = LetterElement.create(letter, null, letterImage, 0, null);
        Participant participant = Participant.create(member, letter, "participant");

        memberRepository.save(member);
        coverTypeRepository.save(coverType);
        fontRepository.save(font);
        letterImageRepository.save(letterImage);
        letterRepository.save(letter);
        LetterElement savedLetterElement = letterElementRepository.save(element);

        //when
        letterElementDomainService.changeContent(participant, savedLetterElement.getId(), "New Letter Element");

        //then
        LetterElement findElement = letterElementRepository.findById(savedLetterElement.getId()).orElse(null);
        assertThat(findElement.getContent()).isEqualTo("New Letter Element");
    }

}
