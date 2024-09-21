package com.ittory.domain.guestbook.repository;

import com.ittory.domain.guestbook.domain.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuestbookRepositoryCustom {
    Page<Guestbook> findGuestbookPage(Pageable pageable);
}
