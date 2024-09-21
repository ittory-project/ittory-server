package com.ittory.api.guestbook.service;

import com.ittory.api.guestbook.dto.GuestbookResponse;
import com.ittory.api.guestbook.dto.GuestbookSaveRequest;
import com.ittory.domain.guestbook.domain.Guestbook;
import com.ittory.domain.guestbook.service.GuestbookDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestbookCreateUseCase {

    private final GuestbookDomainService guestbookDomainService;

    public GuestbookResponse execute(GuestbookSaveRequest guestbookSaveRequest) {
        Guestbook guestbook = guestbookDomainService.saveGuestbook(guestbookSaveRequest.getNickname(),
                guestbookSaveRequest.getContent());
        return GuestbookResponse.from(guestbook);
    }
}
