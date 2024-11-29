package com.ittory.domain.participant.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.participant.enums.ParticipantStatus;
import jakarta.persistence.*;
import lombok.*;

import static com.ittory.domain.participant.enums.ParticipantStatus.GHOST;

@Entity(name = "participant")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Participant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    private String nickname;

    private Integer sequence;

    @Enumerated(EnumType.STRING)
    private ParticipantStatus participantStatus;

    public static Participant create(Member member, Letter letter) {
        return Participant.builder()
                .member(member)
                .letter(letter)
                .participantStatus(GHOST)
                .build();
    }

    public void changeSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public void changeParticipantStatus(ParticipantStatus status) {
        this.participantStatus = status;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

}
