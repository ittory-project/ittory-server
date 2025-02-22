package com.ittory.api.guestbook.service;

import com.ittory.api.guestbook.dto.GuestbookPageResponse;
import com.ittory.api.guestbook.dto.GuestbookResponse;
import com.ittory.api.guestbook.dto.GuestbookSaveRequest;
import com.ittory.domain.guestbook.domain.Guestbook;
import com.ittory.domain.guestbook.service.GuestbookDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookDomainService guestbookDomainService;

    public GuestbookPageResponse findAllGuestbook(Pageable pageable) {
        Page<Guestbook> guestbookPage = guestbookDomainService.findAllGuestbook(pageable);
        List<GuestbookResponse> content = guestbookPage.getContent().stream().map(GuestbookResponse::from).toList();
        return GuestbookPageResponse.of(guestbookPage.hasNext(), guestbookPage.getNumber(), content);
    }

    public GuestbookResponse createGuestbook(GuestbookSaveRequest guestbookSaveRequest) {
        Guestbook guestbook = guestbookDomainService.saveGuestbook(guestbookSaveRequest.getNickname(),
                guestbookSaveRequest.getContent());
        return GuestbookResponse.from(guestbook);
    }


}
