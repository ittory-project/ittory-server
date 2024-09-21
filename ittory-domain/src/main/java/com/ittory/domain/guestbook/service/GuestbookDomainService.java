package com.ittory.domain.guestbook.service;

import com.ittory.domain.guestbook.domain.Guestbook;
import com.ittory.domain.guestbook.domain.GuestbookColor;
import com.ittory.domain.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestbookDomainService {

    private final GuestbookRepository guestbookRepository;

    @Transactional
    public Guestbook saveGuestbook(String nickname, String content) {
        Guestbook guestbook = guestbookRepository.save(Guestbook.create(nickname, content));
        int colorSequence = Integer.parseInt(String.valueOf((guestbook.getId() - 1) % GuestbookColor.values().length));
        guestbook.changeColor(GuestbookColor.values()[colorSequence]);
        return guestbook;
    }

    @Transactional(readOnly = true)
    public Page<Guestbook> findAllGuestbook(Pageable pageable) {
        return guestbookRepository.findGuestbookPage(pageable);
    }
}
