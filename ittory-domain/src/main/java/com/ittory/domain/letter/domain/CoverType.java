package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.dto.CoverTypeImages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "cover_type")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoverType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_type_id")
    private Long id;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "simple_url")
    private String simpleUrl;

    @Column(name = "background_url")
    private String backgroundUrl;

    public static CoverType create(String name, CoverTypeImages request) {
        return CoverType.builder()
                .name(name)
                .imageUrl(request.getImageUrl())
                .simpleUrl(request.getSimpleUrl())
                .backgroundUrl(request.getBackgroundUrl())
                .build();
    }
}
