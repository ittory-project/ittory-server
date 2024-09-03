package com.ittory.api.member.dto;

import com.ittory.domain.member.enums.WithdrawReason;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberWithdrawRequest {
    private WithdrawReason withdrawReason;
    private String content;
}
