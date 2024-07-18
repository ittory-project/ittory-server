package com.ittory.infra.image;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUrlDto {

    private String url;
    private String key;

    public static ImageUrlDto of(String url, String key) {
        return ImageUrlDto.builder()
                .key(key)
                .url(url)
                .build();
    }

}
