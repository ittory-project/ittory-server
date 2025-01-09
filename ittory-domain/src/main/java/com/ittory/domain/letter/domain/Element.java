package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.participant.domain.Participant;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_image_id")
    private ElementImage elementImage;

    private Integer sequence;

    private String content;

    public static Element create(Letter letter, Participant participant, ElementImage elementImage, Integer sequence,
                                 String content) {
        return Element.builder()
                .letter(letter)
                .participant(participant)
                .elementImage(elementImage)
                .sequence(sequence)
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
