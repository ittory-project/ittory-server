package com.ittory.domain.member.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.domain.Letter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "participant")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    private String nickname;

    public static Participant toEntity(Member member, Letter letter) {
        return Participant.builder()
                .member(member)
                .letter(letter)
                .build();
    }

}
