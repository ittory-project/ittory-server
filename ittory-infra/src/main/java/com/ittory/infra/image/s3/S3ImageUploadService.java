package com.ittory.infra.image.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.ittory.infra.image.ImageUploadService;
import com.ittory.infra.image.ImageUrlDto;
import com.ittory.infra.image.ImgExtension;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ImageUploadService implements ImageUploadService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.baseUrl}")
    private String baseUrl = "";

    public ImageUrlDto forLetterCover(ImgExtension imgExtension) {
        String fixedExtension = imgExtension.getUploadExtension();
        String fileName = getForLetterCoverFileName(fixedExtension);
        log.info("Generate PreSigned Url for {}", fileName);
        URL url =
                amazonS3.generatePresignedUrl(
                        getGeneratePreSignedUrlRequest(bucketName, fileName, imgExtension));
        return ImageUrlDto.of(url.toString(), fileName);
    }

    private String getForLetterCoverFileName(String imgExtension) {
        return baseUrl + "/letter/cover/" + UUID.randomUUID() + "." + imgExtension;
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName,
                                                                       ImgExtension imgExtension) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withKey(fileName)
                        .withContentType("image/" + imgExtension.getUploadExtension())
                        .withExpiration(getPreSignedUrlExpiration());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 100000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        log.info("PreSigned Expiration : {}", expiration);
        return expiration;
    }
}
