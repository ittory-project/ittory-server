package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterEnterStatusResponse;
import com.ittory.api.participant.dto.ParticipantNicknameRequest;
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
import org.springframework.transaction.annotation.Transactional;

import static com.ittory.domain.letter.enums.LetterStatus.*;

@Component
@RequiredArgsConstructor
public class LetterEnterUseCase {

    private final LetterDomainService letterDomainService;
    private final MemberDomainService memberDomainService;
    private final ParticipantDomainService participantDomainService;

    // 스케일 아웃 시 분산락 적용 필요
    @Transactional
    public synchronized LetterEnterStatusResponse execute(Long memberId, Long letterId, ParticipantNicknameRequest request) {

        // 1. 편지 상태 확인
        Letter letter = letterDomainService.findLetter(letterId);
        EnterAction enterAction = getEnterAction(letter);
        if (!enterAction.equals(EnterAction.SUCCESS)) {
            return LetterEnterStatusResponse.of(false, enterAction, null, null);
        }

        // 2. 중복 입장 확인
        Participant participant = participantDomainService.findEnterParticipantOrNull(letterId, memberId);
        if (participant != null) {
            throw new ParticipantException.DuplicateParticipantException.DuplicateParticipantException(letterId, memberId);
        }

        // 3. 참여자 수 확인
        Boolean enterStatus = participantDomainService.getEnterStatus(letterId);
        if (!enterStatus) {
            enterAction = EnterAction.EXCEEDED;
            return LetterEnterStatusResponse.of(false, enterAction, null, null);
        }

        // 4. 입장
        Member member = memberDomainService.findMemberById(memberId);
        Participant newParticipant = getNewParticipant(member, letter);

        // 5. 닉네임 설정
        String nickname = setNickname(letterId, request, newParticipant);
        if (nickname != null) {
            return LetterEnterStatusResponse.of(true, enterAction, newParticipant.getId(), nickname);
        } else {
            throw new ParticipantException.DuplicateNicknameException(request.getNickname());
        }
    }

    private String setNickname(Long letterId, ParticipantNicknameRequest request, Participant newParticipant) {
        Boolean isDuplicate = participantDomainService.checkNicknameDuplication(letterId, request.getNickname());

        String nickname = null;
        if (!isDuplicate) {
            newParticipant.changeNickname(request.getNickname());
            participantDomainService.saveParticipant(newParticipant);
            nickname = request.getNickname();
        }
        return nickname;
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
