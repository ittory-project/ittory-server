package com.ittory.socket.usecase;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.config.handler.WebSocketSessionHandler;
import com.ittory.socket.dto.EnterRequest;
import com.ittory.socket.dto.EnterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterEnterUseCase {

    private final WebSocketSessionHandler webSocketSessionHandler;
    private final MemberDomainService memberDomainService;
    private final LetterDomainService letterDomainService;
    private final ParticipantDomainService participantDomainService;


    public EnterResponse execute(Long memberId, Long letterId, EnterRequest request) {
        Participant participant = participantDomainService.findParticipantOrNull(letterId, memberId);

        if (participant != null) {
            throw new IllegalArgumentException("이미 참여중인 사용자");
        }

        Participant newParticipant = createNewParticipant(memberId, letterId, request);
        Participant savedParticipant = participantDomainService.saveParticipant(newParticipant);
        return EnterResponse.from(savedParticipant);
    }

    private Participant createNewParticipant(Long memberId, Long letterId, EnterRequest request) {
        Member member = memberDomainService.findMemberById(memberId);
        Letter letter = letterDomainService.findLetter(letterId);
        return Participant.create(member, letter, request.getNickname());
    }

}
