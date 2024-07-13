package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "cover_type")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverType extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_type_id")
    private Long id;

    private String name;

    private String url;

    public static CoverType toEntity(String name, String url) {
        return CoverType.builder()
                .name(name)
                .url(url)
                .build();
    }
}
