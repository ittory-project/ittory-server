package com.ittory.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.LetterBoxType;
import com.ittory.domain.member.exception.MemberException.LetterBoxAlreadyStoredException;
import com.ittory.domain.member.repository.LetterBoxRepository;
import com.ittory.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LetterBoxDomainServiceTest {

    @Autowired
    private LetterBoxDomainService letterBoxDomainService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private LetterBoxRepository letterBoxRepository;

    @AfterEach
    void clean() {
        letterBoxRepository.deleteAll();
        letterRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("멤버를 저장할 수 있다.")
    @Test
    void saveMemberTest() {
        //given
        Long socialId = 1L;
        String name = "test member";
        String profileImage = "profile url";
        Member member = memberRepository.save(Member.create(socialId, name, profileImage));
        Letter newLetter = Letter.create(null, null, "receiver", null, "title", null);
        Letter letter = letterRepository.save(newLetter);
        letterBoxRepository.save(LetterBox.create(member, letter, LetterBoxType.RECEIVE));

        //when
        Boolean isStored = letterBoxDomainService.checkStorageStatus(letter.getId());

        //then
        assertThat(isStored).isTrue();
    }

    @DisplayName("인원 제한이 2명인 모임에 1명이 이미 참여중이고, 남은 1자리에 10명이 동시에 참여하는 상황")
    @Test
    void saveInLetterBoxTest() throws InterruptedException {
        //given
        Member member1 = memberRepository.save(Member.create(1L, "member1", null));
        Member member2 = memberRepository.save(Member.create(2L, "member2", null));

        Letter newLetter = Letter.create(null, null, "receiver", null, "title", null);
        Letter letter = letterRepository.save(newLetter);

        List<Member> members = List.of(member1, member2);

        int threadCount = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    letterBoxDomainService.saveInReceiveLetterBox(members.get(finalI), letter);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        // then
        List<LetterBox> all = letterBoxRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @DisplayName("이미 RECEIVE LetterBox가 있는 경우 예외 발생")
    @Test
    void saveInLetterBoxTest_LetterBoxAlreadyStoredException() throws InterruptedException {
        //given
        Member member1 = memberRepository.save(Member.create(1L, "member1", null));
        Member member2 = memberRepository.save(Member.create(2L, "member2", null));

        Letter newLetter = Letter.create(null, null, "receiver", null, "title", null);
        Letter letter = letterRepository.save(newLetter);

        List<Member> members = List.of(member1, member2);

        // 첫번째 정상 저장.
        letterBoxDomainService.saveInReceiveLetterBox(members.get(0), letter);

        // when&then
        assertThatThrownBy(
                () -> letterBoxDomainService.saveInReceiveLetterBox(members.get(1), letter))
                .isInstanceOf(LetterBoxAlreadyStoredException.class);
    }

}
