package com.ittory.domain.member.domain;

import static com.ittory.domain.member.enums.ParticipantStatus.PROGRESS;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.enums.ParticipantStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private Integer sort;

    @Enumerated(EnumType.STRING)
    private ParticipantStatus participantStatus;

    public static Participant create(Member member, Letter letter, Integer sort) {
        return Participant.builder()
                .member(member)
                .letter(letter)
                .sort(sort)
                .participantStatus(PROGRESS)
                .build();
    }

    public void changeSort(int sort) {
        this.sort = sort;
    }

}
