package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "letter")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_type_id")
    private CoverType coverType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "font_id")
    private Font font;

    private String receiverName;

    private LocalDateTime deliveryDate;

    private String title;

    private String coverPhotoUrl;

    private Integer repeatCount;

    @Column(name = "letter_status")
    @Enumerated(EnumType.STRING)
    private LetterStatus letterStatus;

    public static Letter create(CoverType coverType, Font font, String receiverName, LocalDateTime deliveryDate,
                                String title, String coverPhotoUrl, Integer repeatCount) {
        return Letter.builder()
                .coverType(coverType)
                .font(font)
                .receiverName(receiverName)
                .deliveryDate(deliveryDate)
                .title(title)
                .coverPhotoUrl(coverPhotoUrl)
                .repeatCount(repeatCount)
                .letterStatus(LetterStatus.WAIT)
                .build();
    }

    public void changeRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void changeStatus(LetterStatus letterStatus) {
        this.letterStatus = letterStatus;
    }

}
