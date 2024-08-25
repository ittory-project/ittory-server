package com.ittory.domain.member.repository;

import com.ittory.domain.member.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByMemberId(Long memberId);
}
