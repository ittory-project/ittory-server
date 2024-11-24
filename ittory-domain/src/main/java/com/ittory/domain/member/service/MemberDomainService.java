package com.ittory.domain.member.service;


import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import com.ittory.domain.member.exception.MemberException.MemberNotFoundException;
import com.ittory.domain.member.repository.LetterBoxRepository;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberDomainService {

    private final MemberRepository memberDomainRepository;
    private final ParticipantRepository participantRepository;
    private final LetterRepository letterRepository;
    private final LetterBoxRepository letterBoxRepository;


    @Transactional
    public Member saveMember(Long socialId, String name, String profileImage) {
        return memberDomainRepository.save(Member.create(socialId, name, profileImage));
    }

    @Transactional(readOnly = true)
    public Member findMemberById(Long memberId) {
        return memberDomainRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Transactional(readOnly = true)
    public Member findMemberBySocialId(Long socialId) {
        return memberDomainRepository.findBySocialId(socialId).orElse(null);
    }

    @Transactional
    public void changeRefreshToken(Member member, String memberRefreshToken) {
        member.changeRefreshToken(memberRefreshToken);
        memberDomainRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<Letter> getParticipatedLetters(Long memberId) {
        return participantRepository.findByMemberId(memberId).stream()
                .map(Participant::getLetter)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Letter> getReceivedLetters(Long memberId) {
        return letterBoxRepository.findAllByReceiverId(memberId) // LetterBox 조회
                .stream()
                .map(LetterBox::getLetter) // LetterBox에서 Letter 객체 추출
                .collect(Collectors.toList());
    }

    @Transactional
    public void withdrawMember(Member member) {
        member.changeSocialId(null);
        member.changeStatus(MemberStatus.DELETED);
        memberDomainRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Boolean checkVisitedMember(Long memberId) {
        Integer participationLetterCount = letterBoxRepository.countParticipationLetterByMemberId(memberId);
        return participationLetterCount > 0;
    }
}
