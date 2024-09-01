package com.ittory.domain.participant.repository;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.repository.impl.ParticipantRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long>, ParticipantRepositoryCustom {
    List<Participant> findByMemberId(Long memberId);
}
