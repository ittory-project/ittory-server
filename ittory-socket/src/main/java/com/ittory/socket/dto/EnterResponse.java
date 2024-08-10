package com.ittory.socket.dto;

import static com.ittory.socket.enums.ConnectAction.ENTER;

import com.ittory.domain.member.domain.Participant;
import com.ittory.socket.enums.ConnectAction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EnterResponse {

    private Long memberId;
    private String name;
    private String imageUrl;
    private ConnectAction action;

    public static EnterResponse from(Participant participant) {
        return EnterResponse.builder()
                .memberId(participant.getMember().getId())
                .name(participant.getNickname())
                .imageUrl(participant.getMember().getProfileImage())
                .action(ENTER)
                .build();
    }
}
