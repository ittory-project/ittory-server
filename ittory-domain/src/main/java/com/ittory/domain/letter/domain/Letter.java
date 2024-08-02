package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "letter")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long Id;

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

    public static Letter create(CoverType coverType, Font font, String receiverName, LocalDateTime deliveryDate,
                                String title, String coverPhotoUrl) {
        return Letter.builder()
                .coverType(coverType)
                .font(font)
                .receiverName(receiverName)
                .deliveryDate(deliveryDate)
                .title(title)
                .coverPhotoUrl(coverPhotoUrl)
                .build();
    }



}
