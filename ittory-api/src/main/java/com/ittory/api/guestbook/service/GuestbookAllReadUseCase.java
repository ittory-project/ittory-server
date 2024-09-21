package com.ittory.api.guestbook.service;

import com.ittory.api.guestbook.dto.GuestbookPageResponse;
import com.ittory.api.guestbook.dto.GuestbookResponse;
import com.ittory.domain.guestbook.domain.Guestbook;
import com.ittory.domain.guestbook.service.GuestbookDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestbookAllReadUseCase {

    private final GuestbookDomainService guestbookDomainService;

    public GuestbookPageResponse execute(Pageable pageable) {
        Page<Guestbook> guestbookPage = guestbookDomainService.findAllGuestbook(pageable);
        List<GuestbookResponse> content = guestbookPage.getContent().stream().map(GuestbookResponse::from).toList();
        return GuestbookPageResponse.of(guestbookPage.hasNext(), guestbookPage.getNumber(), content);
    }

}
