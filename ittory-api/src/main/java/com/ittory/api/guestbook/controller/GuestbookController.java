package com.ittory.api.guestbook.controller;

import com.ittory.api.guestbook.dto.GuestbookResponse;
import com.ittory.api.guestbook.dto.GuestbookSaveRequest;
import com.ittory.api.guestbook.service.GuestbookCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guestbook")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookCreateUseCase guestbookCreateUseCase;

    @PostMapping
    public ResponseEntity<GuestbookResponse> saveGuestbook(@RequestBody GuestbookSaveRequest guestbookSaveRequest) {
        GuestbookResponse response = guestbookCreateUseCase.execute(guestbookSaveRequest);
        return ResponseEntity.ok().body(response);
    }
}
