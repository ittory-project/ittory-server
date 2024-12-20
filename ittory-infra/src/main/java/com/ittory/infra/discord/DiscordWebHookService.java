package com.ittory.infra.discord;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import com.ittory.domain.member.enums.WithdrawReason;
import com.ittory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscordWebHookService implements WebHookService {
    @Value("${discord.webhook.signup}")
    private String DISCORD_WEBHOOK_SIGNUP_URL;
    @Value("${discord.webhook.withdraw}")
    private String DISCORD_WEBHOOK_WITHDRAW_URL;

    private final DiscordWebHookConnector discordWebHookConnector;
    private final MemberRepository memberRepository;

    @Override
    @Async("asyncThreadPoolExecutor")
    @Transactional
    public void sendSingupMessage(Member member) {
        long activeUser = memberRepository.countByMemberStatus(MemberStatus.ACTIVE);
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime localTime = now.toLocalTime().truncatedTo(ChronoUnit.SECONDS);
        String message = new StringBuilder()
                .append("# 유저가 가입하였습니다.\n")
                .append("- 이름: ").append(member.getName()).append("\n")
                .append("- 가입 시간: ").append(date).append(" ").append(localTime).append("\n")
                .append("### 현재 __**").append(activeUser).append("명**__의 유저")
                .toString();

        WebHookMessage webHookMessage = new WebHookMessage(message);

        discordWebHookConnector.sendMessageForDiscord(webHookMessage, DISCORD_WEBHOOK_SIGNUP_URL);

    }

    @Override
    @Async("asyncThreadPoolExecutor")
    @Transactional
    public void sendWithdrawMessage(Member member, WithdrawReason reason, String content) {
        long activeUser = memberRepository.countByMemberStatus(MemberStatus.ACTIVE);
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime localTime = now.toLocalTime().truncatedTo(ChronoUnit.SECONDS);
        String message = new StringBuilder()
                .append("# 유저가 탈퇴하였습니다.\n")
                .append("- 이름: ").append(member.getName()).append("\n")
                .append("- 탈퇴 시간: ").append(date).append(" ").append(localTime).append("\n")
                .append("- 탈퇴 사유: ").append(reason).append(" -> ").append(content).append("\n")
                .append("### 현재 __**").append(activeUser).append("명**__의 유저").append("\n")
                .toString();

        WebHookMessage webHookMessage = new WebHookMessage(message);

        discordWebHookConnector.sendMessageForDiscord(webHookMessage, DISCORD_WEBHOOK_WITHDRAW_URL);

    }

}
