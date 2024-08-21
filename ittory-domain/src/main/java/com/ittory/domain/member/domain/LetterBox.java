package com.ittory.domain.member.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.enums.LetterBoxType;
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

@Entity(name = "letter_box")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterBox extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    @Enumerated(EnumType.STRING)
    private LetterBoxType letterBoxType;

    public static LetterBox create(Member member, Letter letter, LetterBoxType type) {
        return LetterBox.builder()
                .member(member)
                .letter(letter)
                .letterBoxType(type)
                .build();
    }

}
