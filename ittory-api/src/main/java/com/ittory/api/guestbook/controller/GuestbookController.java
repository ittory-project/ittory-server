package com.ittory.api.guestbook.controller;

import com.ittory.api.guestbook.dto.GuestbookPageResponse;
import com.ittory.api.guestbook.dto.GuestbookResponse;
import com.ittory.api.guestbook.dto.GuestbookSaveRequest;
import com.ittory.api.guestbook.service.GuestbookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guestbook")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @Operation(summary = "방명록 저장", description = "방명록 DB Id 기준으로 Green -> Red -> Blue -> Yellow 순으로 색 지정")
    @PostMapping
    public ResponseEntity<GuestbookResponse> saveGuestbook(@RequestBody GuestbookSaveRequest guestbookSaveRequest) {
        GuestbookResponse response = guestbookService.createGuestbook(guestbookSaveRequest);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "방명록 리스트 조회", description = "(Pagination) 생성일 기준으로 내림차순 조회")
    @GetMapping("/all")
    public ResponseEntity<GuestbookPageResponse> getAllGuestbook(Pageable pageable) {
        GuestbookPageResponse response = guestbookService.findAllGuestbook(pageable);
        return ResponseEntity.ok().body(response);
    }

}
