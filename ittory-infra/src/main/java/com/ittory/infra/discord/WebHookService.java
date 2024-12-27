package com.ittory.infra.discord;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.WithdrawReason;

public interface WebHookService {
    void sendSingupMessage(Member member);

    void sendWithdrawMessage(Member member, WithdrawReason reason, String content);

    void sendDailyReportMessage();

    void sendNPlusOneOccurrence(String message);

}
