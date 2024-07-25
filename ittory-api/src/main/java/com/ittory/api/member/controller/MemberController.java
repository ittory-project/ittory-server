package com.ittory.api.member.controller;

import com.ittory.api.member.dto.MemberSearchResponse;
import com.ittory.api.member.usecase.MemberUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/app/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberUserCase memberUserCase;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberSearchResponse> searchMemberById(@PathVariable("memberId") Long memberId) {
        MemberSearchResponse response = memberUserCase.searchMemberById(memberId);
        return ResponseEntity.ok().body(response);
    }
}
