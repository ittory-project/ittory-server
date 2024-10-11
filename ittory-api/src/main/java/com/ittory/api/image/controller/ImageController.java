package com.ittory.api.image.controller;

import com.ittory.api.image.dto.ImageUrlRequest;
import com.ittory.api.image.dto.ImageUrlResponse;
import com.ittory.api.image.service.GetImageUploadUrlUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final GetImageUploadUrlUseCase getImageUploadUrlUseCase;

    @Operation(summary = "이미지 S3 PreSigned URL 발급", description = "JPG, JPEG, PNG만 가능")
    @PostMapping("/letter-cover")
    public ResponseEntity<ImageUrlResponse> getLetterCoverImageUlr(@RequestBody ImageUrlRequest imageUrlRequest) {
        ImageUrlResponse response = getImageUploadUrlUseCase.forLetterCover(imageUrlRequest.getImgExtension());
        return ResponseEntity.ok().body(response);
    }

}
