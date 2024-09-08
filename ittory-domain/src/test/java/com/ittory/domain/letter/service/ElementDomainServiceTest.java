package com.ittory.domain.letter.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.domain.ElementImage;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.dto.CoverTypeImages;
import com.ittory.domain.letter.dto.ElementEditData;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import com.ittory.domain.letter.repository.ElementImageRepository;
import com.ittory.domain.letter.repository.ElementRepository;
import com.ittory.domain.letter.repository.FontRepository;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.repository.ParticipantRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElementDomainServiceTest {

    @Autowired
    private ElementDomainService elementDomainService;

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private CoverTypeRepository coverTypeRepository;

    @Autowired
    private FontRepository fontRepository;

    @Autowired
    private ElementImageRepository elementImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipantRepository participantRepository;


    @AfterEach
    void clean() {
        elementRepository.deleteAll();
        participantRepository.deleteAll();
        letterRepository.deleteAll();
        coverTypeRepository.deleteAll();
        fontRepository.deleteAll();
        elementImageRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("편지 Element의 내용을 변경할 수 있다.")
    @Test
    void changeContentTest() {
        //given
        Member member = Member.create(1L, "member", "image");
        CoverTypeImages images = CoverTypeImages.of("image", "simple", "back");
        CoverType coverType = CoverType.create("type", images);
        Font font = Font.create("font");
        ElementImage elementImage = ElementImage.create("image");

        Letter letter = Letter.create(coverType, font, "receiver", LocalDateTime.now(), "title", "image", null);
        Participant participant = Participant.create(member, letter, "participant");
        Element element = Element.create(letter, participant, elementImage, 1, null);

        memberRepository.save(member);
        coverTypeRepository.save(coverType);
        fontRepository.save(font);
        elementImageRepository.save(elementImage);
        letterRepository.save(letter);
        participantRepository.save(participant);
        Element savedElement = elementRepository.save(element);

        ElementEditData editData = ElementEditData.of(1, "New Letter Element");

        //when
        elementDomainService.changeContent(letter.getId(), participant, editData);

        //then
        Element findElement = elementRepository.findById(savedElement.getId()).orElse(null);
        assertThat(findElement.getContent()).isEqualTo("New Letter Element");
    }

}
