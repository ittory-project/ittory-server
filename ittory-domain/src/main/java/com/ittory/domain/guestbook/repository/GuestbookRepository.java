package com.ittory.domain.guestbook.repository;

import com.ittory.domain.guestbook.domain.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, GuestbookRepositoryCustom {
}
