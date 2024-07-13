package com.ittory.api.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberCreateRequest {
    private String email;
    private String name;
}
