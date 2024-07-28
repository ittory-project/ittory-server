package com.ittory.api.letter.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LetterCreateRequest {
    private Long coverTypeId;
    private Long fontId;
    private Long receiverId;
    private String receiverName;
    private LocalDateTime deliveryDate;
    private String title;
    private String coverPhotoUrl;
}
