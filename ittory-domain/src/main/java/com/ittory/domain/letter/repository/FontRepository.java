package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.Font;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FontRepository extends JpaRepository<Font, Long> {
}