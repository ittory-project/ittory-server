package com.ittory.api.letter.service;

import com.ittory.api.letter.dto.*;
import com.ittory.api.participant.dto.ParticipantNicknameRequest;
import com.ittory.api.participant.dto.ParticipantProfile;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.enums.EnterAction;
import com.ittory.domain.participant.enums.ParticipantStatus;
import com.ittory.domain.participant.exception.ParticipantException;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ittory.domain.letter.enums.LetterStatus.*;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterDomainService letterDomainService;
    private final ElementDomainService elementDomainService;
    private final ParticipantDomainService participantDomainService;
    private final MemberDomainService memberDomainService;
    private final LetterBoxDomainService letterBoxDomainService;

    @Transactional
    public LetterCreateResponse createLetter(LetterCreateRequest request) {
        Letter letter = letterDomainService.saveLetter(
                request.getCoverTypeId(),
                request.getFontId(),
                request.getReceiverName(),
                request.getDeliveryDate(),
                request.getTitle(),
                request.getCoverPhotoUrl()
        );
        return LetterCreateResponse.from(letter);
    }

    public void deleteLetter(Long letterId) {
        letterDomainService.deleteLetter(letterId);
    }

    @Transactional(readOnly = true)
    public LetterDetailResponse getLetterForDetails(Long letterId) {
        Letter letter = letterDomainService.findLetterById(letterId);
        List<Element> elements = elementDomainService.findAllByLetterId(letterId);
        List<String> nicknames = participantDomainService.findAllParticipants(letterId).stream()
                .map(Participant::getNickname)
                .toList();

        return LetterDetailResponse.of(letter, nicknames, elements);
    }

    public LetterEnterStatusResponse checkEnterStatus(Long letterId) {
        Boolean enterStatus = participantDomainService.getEnterStatus(letterId);
        return LetterEnterStatusResponse.of(enterStatus);
    }

    // 스케일 아웃 시 분산락 적용 필요
    @Transactional
    public synchronized LetterEnterStatusResponse enterLetter(Long memberId, Long letterId, ParticipantNicknameRequest request) {

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
        return LetterEnterStatusResponse.of(true, enterAction, newParticipant.getId(), nickname);
        /*
            // TODO : 프론트 대기실 접속 로직 반영 후 삭제 예정 25.02.12 - by junker
            if (nickname != null) {
                return LetterEnterStatusResponse.of(true, enterAction, newParticipant.getId(), nickname);
            } else {
                throw new ParticipantException.DuplicateNicknameException(request.getNickname());
            }
         */
    }

    private String setNickname(Long letterId, ParticipantNicknameRequest request, Participant newParticipant) {
        // TODO : 프론트 대기실 접속 로직 반영 후 삭제 예정 25.02.12 - by junker
        if (request == null) {
            return null;
        }
        // TODO : ============
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

    public LetterInfoResponse getLetterInfo(Long letterId) {
        Letter letter = letterDomainService.findLetterById(letterId);
        return LetterInfoResponse.from(letter);
    }

    @Transactional(readOnly = true)
    public List<ParticipantProfile> getAllParticipantProfile(Long letterId, String order) {
        Boolean isAscending = null;
        if (order != null && order.equals("sequence")) {
            isAscending = true;
        }
        return participantDomainService.findAllCurrentParticipantsOrderedBySequence(letterId, isAscending)
                .stream()
                .map(ParticipantProfile::from)
                .toList();
    }

    @Transactional
    public void changeLetterRepeatCount(LetterRepeatCountRequest request) {
        Letter letter = letterDomainService.findLetter(request.getLetterId());
        elementDomainService.deleteAllByLetterId(letter.getId());
        letterDomainService.changeRepeatCount(letter, request.getRepeatCount());
        letterDomainService.createLetterElements(letter, request.getRepeatCount());
    }

    @Transactional(readOnly = true)
    public LetterStartInfoResponse getLetterStartInfo(Long letterId) {
        Letter letter = letterDomainService.findLetter(letterId);
        List<Element> elements = letterDomainService.findElementsByLetterId(letterId);
        Integer participantCount = participantDomainService.countProgressByLetterId(letterId);
        return LetterStartInfoResponse.of(letter.getTitle(), participantCount, letter.getRepeatCount(), elements.size());
    }

    @Transactional(readOnly = true)
    public LetterStorageStatusResponse getLetterStorageStatus(Long letterId) {
        Letter letter = letterDomainService.findLetter(letterId);
        Boolean isStored = letterBoxDomainService.checkStorageStatus(letter.getId());
        return LetterStorageStatusResponse.of(isStored, letter.getReceiverName());
    }

    public void storeInLetterBox(Long memberId, Long letterId) {
        Member member = memberDomainService.findMemberById(memberId);
        Letter letter = letterDomainService.findLetter(letterId);
        letterBoxDomainService.saveInReceiveLetterBox(member, letter);
    }

}
