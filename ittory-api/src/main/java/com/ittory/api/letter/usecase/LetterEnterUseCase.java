package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterEnterStatusResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.enums.EnterAction;
import com.ittory.domain.participant.enums.ParticipantStatus;
import com.ittory.domain.participant.exception.ParticipantException;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.ittory.domain.letter.enums.LetterStatus.*;

@Component
@RequiredArgsConstructor
public class LetterEnterUseCase {

    private final LetterDomainService letterDomainService;
    private final MemberDomainService memberDomainService;
    private final ParticipantDomainService participantDomainService;

    // 스케일 아웃 시 분산락 적용 필요
    public synchronized LetterEnterStatusResponse execute(Long memberId, Long letterId) {
        Boolean enterStatus = participantDomainService.getEnterStatus(letterId);
        EnterAction enterAction = EnterAction.EXCEEDED;

        Long participantId = null;
        if (enterStatus) {
            enterStatus = false;
            // 중복 참여 검증
            Participant participant = participantDomainService.findEnterParticipantOrNull(letterId, memberId);
            if (participant != null) {
                throw new ParticipantException.DuplicateParticipantException.DuplicateParticipantException(letterId, memberId);
            }

            // 입장
            Member member = memberDomainService.findMemberById(memberId);
            Letter letter = letterDomainService.findLetter(letterId);
            enterAction = getEnterAction(letter);

            if (enterAction.equals(EnterAction.SUCCESS)) {
                Participant newParticipant = getNewParticipant(member, letter);
                participantId = newParticipant.getId();
                enterStatus = true;
            }
        }

        return LetterEnterStatusResponse.of(enterStatus, enterAction, participantId);
    }

    private Participant getNewParticipant(Member member, Letter letter) {
        Participant oldParticipant = participantDomainService.findParticipantOrNull(letter.getId(), member.getId());
        if (oldParticipant != null) {
            updateToGhostParticipant(oldParticipant);
            return oldParticipant;
        }
        return participantDomainService.saveParticipant(Participant.create(member, letter));
    }

    private void updateToGhostParticipant(Participant oldParticipant) {
        oldParticipant.changeParticipantStatus(ParticipantStatus.GHOST);
        participantDomainService.saveParticipant(oldParticipant);
    }

    private static EnterAction getEnterAction(Letter letter) {
        LetterStatus letterStatus = letter.getLetterStatus();
        EnterAction enterAction;
        if (letterStatus.equals(PROGRESS) || letterStatus.equals(COMPLETED)) {
            enterAction = EnterAction.STARTED;
        } else if (letterStatus.equals(DELETED)) {
            enterAction = EnterAction.DELETED;
        } else {
            enterAction = EnterAction.SUCCESS;
        }
        return enterAction;
    }

}
