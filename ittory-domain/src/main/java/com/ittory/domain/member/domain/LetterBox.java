package com.ittory.domain.member.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.enums.LetterBoxType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "letter_box")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterBox extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_box_id")
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
