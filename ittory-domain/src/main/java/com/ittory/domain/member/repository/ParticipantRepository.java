package com.ittory.domain.member.repository;

import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.repository.impl.ParticipantRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long>, ParticipantRepositoryCustom {
}
