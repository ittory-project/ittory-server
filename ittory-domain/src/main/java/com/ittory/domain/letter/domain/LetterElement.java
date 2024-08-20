package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.member.domain.Participant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "letter_element")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterElement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    private Participant participant;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "letter_image_id")
    private LetterImage letterImage;

    private Integer sort;

    private String content;

    public static LetterElement create(Letter letter, Participant participant, LetterImage letterImage, Integer sort,
                                       String content) {
        return LetterElement.builder()
                .letter(letter)
                .participant(participant)
                .letterImage(letterImage)
                .sort(sort)
                .content(content)
                .build();
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeParticipant(Participant participant) {
        this.participant = participant;
    }

}
