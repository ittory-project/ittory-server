package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.CoverType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoverTypeRepository extends JpaRepository<CoverType, Long> {
}
