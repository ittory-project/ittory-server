package com.ittory.api.guestbook.controller;

import com.ittory.api.guestbook.dto.GuestbookPageResponse;
import com.ittory.api.guestbook.dto.GuestbookResponse;
import com.ittory.api.guestbook.dto.GuestbookSaveRequest;
import com.ittory.api.guestbook.service.GuestbookAllReadUseCase;
import com.ittory.api.guestbook.service.GuestbookCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guestbook")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookCreateUseCase guestbookCreateUseCase;
    private final GuestbookAllReadUseCase guestbookAllReadUseCase;

    @PostMapping
    public ResponseEntity<GuestbookResponse> saveGuestbook(@RequestBody GuestbookSaveRequest guestbookSaveRequest) {
        GuestbookResponse response = guestbookCreateUseCase.execute(guestbookSaveRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<GuestbookPageResponse> getAllGuestbook(Pageable pageable) {
        GuestbookPageResponse response = guestbookAllReadUseCase.execute(pageable);
        return ResponseEntity.ok().body(response);
    }

}
