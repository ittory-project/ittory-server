package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterEnterStatusResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.exception.ParticipantException;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LetterEnterUseCase {

    private final LetterDomainService letterDomainService;
    private final MemberDomainService memberDomainService;
    private final ParticipantDomainService participantDomainService;

    // 스케일 아웃 시 분산락 적용 필요
    public synchronized LetterEnterStatusResponse execute(Long memberId, Long letterId) {
        Boolean enterStatus = participantDomainService.getEnterStatus(letterId);

        Long participantId = null;
        if (enterStatus) {
            // 중복 참여 검증
            Participant participant = participantDomainService.findParticipantOrNull(letterId, memberId);
            if (participant != null) {
                throw new ParticipantException.DuplicateParticipantException.DuplicateParticipantException(letterId, memberId);
            }

            // 입장
            Member member = memberDomainService.findMemberById(memberId);
            Letter letter = letterDomainService.findLetter(letterId);
            Participant newParticipant = participantDomainService.saveParticipant(Participant.create(member, letter));
            participantId = newParticipant.getId();
        }

        return LetterEnterStatusResponse.of(enterStatus, participantId);
    }

}
