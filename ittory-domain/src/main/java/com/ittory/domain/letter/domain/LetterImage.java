package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "letter_image")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LetterImage extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    public static LetterImage toEntity(String url) {
        return LetterImage.builder()
                .url(url)
                .build();
    }

}
