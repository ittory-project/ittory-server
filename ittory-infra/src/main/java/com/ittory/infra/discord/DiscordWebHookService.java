package com.ittory.infra.discord;

import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import com.ittory.domain.member.enums.WithdrawReason;
import com.ittory.domain.member.repository.LetterBoxRepository;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.member.repository.MemberWithdrawRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Value("${discord.webhook.daily-report}")
    private String DISCORD_WEBHOOK_DAILY_REPORT_URL;
    @Value("${discord.webhook.nplusone-occurrence}")
    private String DISCORD_WEBHOOK_NPLUSONE_OCCURRENCE_URL;
    @Value("${discord.webhook.error}")
    private String DISCORD_ERROR_URL;

    private final DiscordWebHookConnector discordWebHookConnector;
    private final MemberRepository memberRepository;
    private final MemberWithdrawRepository memberWithdrawRepository;
    private final LetterRepository letterRepository;
    private final LetterBoxRepository letterBoxRepository;

    @Override
    @Async("asyncThreadPoolExecutor")
    @Transactional
    public void sendSingupMessage(Member member) {
        if (DISCORD_WEBHOOK_SIGNUP_URL != null && !DISCORD_WEBHOOK_SIGNUP_URL.isEmpty()) {
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

    }

    @Override
    @Async("asyncThreadPoolExecutor")
    @Transactional
    public void sendWithdrawMessage(Member member, WithdrawReason reason, String content) {
        if (DISCORD_WEBHOOK_WITHDRAW_URL != null && !DISCORD_WEBHOOK_WITHDRAW_URL.isEmpty()) {
            long activeUser = memberRepository.countByMemberStatus(MemberStatus.ACTIVE);
            LocalDateTime now = LocalDateTime.now();
            LocalDate date = now.toLocalDate();
            LocalTime localTime = now.toLocalTime().truncatedTo(ChronoUnit.SECONDS);
            String message = new StringBuilder()
                    .append("# 유저가 탈퇴하였습니다.\n")
                    .append("- 이름: ").append(member.getName()).append("\n")
                    .append("- 가입 시간: ").append(member.getCreatedAt()).append("\n")
                    .append("- 탈퇴 시간: ").append(date).append(" ").append(localTime).append("\n")
                    .append("- 탈퇴 사유: ").append(reason).append(" -> ").append(content).append("\n")
                    .append("### 현재 __**").append(activeUser).append("명**__의 유저").append("\n")
                    .toString();

            WebHookMessage webHookMessage = new WebHookMessage(message);

            discordWebHookConnector.sendMessageForDiscord(webHookMessage, DISCORD_WEBHOOK_WITHDRAW_URL);
        }

    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void sendDailyReportMessage() {
        if (DISCORD_WEBHOOK_DAILY_REPORT_URL != null && !DISCORD_WEBHOOK_DAILY_REPORT_URL.isEmpty()) {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);
            long yesterdaySignUpCount = memberRepository.countSignUpByDate(yesterday);
            long yesterdayWithdrawCount = memberWithdrawRepository.countWithdrawByDate(yesterday);
            long yesterdayCreatedLetterCount = letterRepository.countByCreatedAt(yesterday);
            long yesterdaySavedReceiveLetterCount = letterBoxRepository.countReceivedLetterByCreatedAt(yesterday);
            String message = new StringBuilder()
                    .append("# " + today + "데일리 보고서!\n")
                    .append("- 어제 가입한 회원 수: ").append(yesterdaySignUpCount).append("\n")
                    .append("- 어제 탈퇴한 회원 수: ").append(yesterdayWithdrawCount).append("\n")
                    .append("- 어제 생성된 편지 수: ").append(yesterdayCreatedLetterCount).append("\n")
                    .append("- 어제 받은 편지함에 저장된 편지 수: ").append(yesterdaySavedReceiveLetterCount)
                    .toString();

            WebHookMessage webHookMessage = new WebHookMessage(message);

            discordWebHookConnector.sendMessageForDiscord(webHookMessage, DISCORD_WEBHOOK_DAILY_REPORT_URL);
        }

    }

    @Override
    @Async("asyncThreadPoolExecutor")
    @Transactional
    public void sendNPlusOneOccurrence(String message) {
        if (DISCORD_WEBHOOK_NPLUSONE_OCCURRENCE_URL != null && !DISCORD_WEBHOOK_NPLUSONE_OCCURRENCE_URL.isEmpty()) {
            WebHookMessage webHookMessage = new WebHookMessage(message);
            discordWebHookConnector.sendMessageForDiscord(webHookMessage, DISCORD_WEBHOOK_NPLUSONE_OCCURRENCE_URL);
        }

    }

    @Override
    @Async("asyncThreadPoolExecutor")
    @Transactional
    public void sendErrorLog(String message) {
        if (DISCORD_ERROR_URL != null && !DISCORD_ERROR_URL.isEmpty()) {
            WebHookMessage webHookMessage = new WebHookMessage(message);
            discordWebHookConnector.sendMessageForDiscord(webHookMessage, DISCORD_ERROR_URL);
        }
    }
}
