package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.participant.domain.Participant;
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

@Entity(name = "element")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Element extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "element_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    private Participant participant;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "element_image_id")
    private ElementImage elementImage;

    private Integer sort;

    private String content;

    public static Element create(Letter letter, Participant participant, ElementImage elementImage, Integer sort,
                                 String content) {
        return Element.builder()
                .letter(letter)
                .participant(participant)
                .elementImage(elementImage)
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
