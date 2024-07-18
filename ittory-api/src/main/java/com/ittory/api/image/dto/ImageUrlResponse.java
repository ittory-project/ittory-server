package com.ittory.api.image.dto;

import com.ittory.infra.image.ImageUrlDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUrlResponse {

    private String preSignedUrl;
    private String key;

    public static ImageUrlResponse from(ImageUrlDto imageUrlDto) {
        return ImageUrlResponse.builder()
                .preSignedUrl(imageUrlDto.getUrl())
                .key(imageUrlDto.getKey())
                .build();
    }
}
