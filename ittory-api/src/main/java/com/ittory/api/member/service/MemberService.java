package com.ittory.api.member.service;

import com.ittory.api.member.dto.*;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.member.service.MemberWithdrawDomainService;
import com.ittory.infra.discord.DiscordWebHookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDomainService memberDomainService;
    private final LetterBoxDomainService letterBoxDomainService;
    private final MemberWithdrawDomainService memberWithdrawDomainService;
    private final DiscordWebHookService discordWebHookService;

    public MemberDetailResponse getMemberDetails(Long memberId) {
        return MemberDetailResponse.from(memberDomainService.findMemberById(memberId));
    }

    public MemberLetterCountResponse getMemberLetterCount(Long memberId) {
        Integer participationLetterCount = letterBoxDomainService.countParticipationLetterByMemberId(memberId);
        Integer receiveLetterCount = letterBoxDomainService.countReceiveLetterByMemberId(memberId);
        return MemberLetterCountResponse.of(participationLetterCount, receiveLetterCount);
    }

    public MemberAlreadyVisitResponse getMemberAlreadyVisitStatus(Long memberId) {
        memberDomainService.findMemberById(memberId);
        Boolean isVisited = memberDomainService.checkVisitedMember(memberId);
        return MemberAlreadyVisitResponse.from(isVisited);
    }

    public void withdrawMember(Long memberId, MemberWithdrawRequest request) {
        Member member = memberDomainService.findMemberById(memberId);
        memberDomainService.withdrawMember(member);
        memberWithdrawDomainService.saveMemberWithdraw(member, request.getWithdrawReason(), request.getContent());
        discordWebHookService.sendWithdrawMessage(member, request.getWithdrawReason(), request.getContent());
    }

    @Transactional(readOnly = true)
    public ParticipationResponse getMemberParticipatedLetters(Long memberId) {
        List<Letter> participatedLetters = memberDomainService.getParticipatedLetters(memberId);
        return ParticipationResponse.of(participatedLetters);
    }

    public ReceivedLetterResponse getMemberReceivedLetters(Long memberId) {
        return ReceivedLetterResponse.of(
                memberDomainService.getReceivedLetters(memberId)
        );
    }


}
