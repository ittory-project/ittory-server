package com.ittory.api.image.service;

import com.ittory.api.image.dto.ImageUrlResponse;
import com.ittory.infra.image.ImageUploadService;
import com.ittory.infra.image.ImgExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageUrlService {

    private final ImageUploadService imageUploadService;

    public ImageUrlResponse forLetterCover(ImgExtension imgExtension) {
        return ImageUrlResponse.from(imageUploadService.forLetterCover(imgExtension));
    }

}
