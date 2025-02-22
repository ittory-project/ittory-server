package com.ittory.api.image.controller;

import com.ittory.api.image.dto.ImageUrlRequest;
import com.ittory.api.image.dto.ImageUrlResponse;
import com.ittory.api.image.service.ImageUrlService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageUrlService imageUrlService;

    @Operation(summary = "이미지 S3 PreSigned URL 발급", description = "JPG, JPEG, PNG만 가능." +
            " 발급된 PreSigned URL로 PUT과 Binary Body로 이미지를 보내면 발급." +
            " 이미지 URL : {AWS S3 주소} + key. AWS S3 주소는 Confluence의 프로젝트 정보를 참고.")
    @PostMapping("/letter-cover")
    public ResponseEntity<ImageUrlResponse> getLetterCoverImageUlr(@RequestBody ImageUrlRequest imageUrlRequest) {
        ImageUrlResponse response = imageUrlService.forLetterCover(imageUrlRequest.getImgExtension());
        return ResponseEntity.ok().body(response);
    }

}
