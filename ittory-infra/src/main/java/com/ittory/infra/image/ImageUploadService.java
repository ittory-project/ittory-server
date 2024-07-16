package com.ittory.infra.image;

import org.springframework.stereotype.Service;

@Service
public interface ImageUploadService {
    ImageUrlDto forLetterCover(ImgExtension imgExtension);
}
