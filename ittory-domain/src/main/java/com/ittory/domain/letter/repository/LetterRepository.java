package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.impl.LetterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long>, LetterRepositoryCustom {
    List<Letter> findByReceiverIdOrderByDeliveryDateDesc(Long receiverId);
}