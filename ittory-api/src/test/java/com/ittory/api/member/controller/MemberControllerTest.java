package com.ittory.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ittory.api.member.dto.*;
import com.ittory.api.member.service.LetterBoxService;
import com.ittory.api.member.service.MemberService;
import com.ittory.common.jwt.AccessTokenInfo;
import com.ittory.common.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LetterBoxService letterBoxService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtProvider jwtProvider;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String ACCESS_TOKEN = "access_token";
    private static final String AUTH_HEADER = "Authorization";

    @DisplayName("참여자 편지함에서 편지 삭제")
    @Test
    public void deleteLetterInLetterBoxTest() throws Exception {
        //given
        Long letterId = 1L;
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);
        doNothing().when(letterBoxService).deleteLetterInLetterBox(1L, letterId);

        // when & then
        mockMvc.perform(delete("/api/member/{letterId}", letterId)
                        .header(AUTH_HEADER, ACCESS_TOKEN))
                .andExpect(status().isOk());

        verify(letterBoxService).deleteLetterInLetterBox(1L, letterId);

    }

    @Test
    @DisplayName("마이페이지 정보 조회")
    void getMyPageTest() throws Exception {
        // given
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");
        Long memberId = 1L;
        String memberName = "tester";
        MemberDetailResponse response = MemberDetailResponse.builder()
                .memberId(memberId)
                .name(memberName)
                .build();

        when(memberService.getMemberDetails(memberId)).thenReturn(response);
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);


        // when & then
        mockMvc.perform(get("/api/member/mypage")
                        .header(AUTH_HEADER, ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andExpect(jsonPath("$.data.name").value(memberName));

        verify(memberService).getMemberDetails(memberId);
    }

    @Test
    @DisplayName("참여한 편지 조회")
    void getParticipationsTest() throws Exception {
        // given
        Long memberId = 1L;
        ParticipationResponse response = new ParticipationResponse();
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");
        when(memberService.getMemberParticipatedLetters(memberId)).thenReturn(response);
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);


        // when & then
        mockMvc.perform(get("/api/member/participations")
                        .header(AUTH_HEADER, ACCESS_TOKEN))
                .andExpect(status().isOk());

        verify(memberService).getMemberParticipatedLetters(memberId);
    }

    @Test
    @DisplayName("받은 편지함 조회")
    void getReceivedLettersTest() throws Exception {
        // given
        Long memberId = 1L;
        ReceivedLetterResponse response = new ReceivedLetterResponse();
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");
        when(memberService.getMemberReceivedLetters(memberId)).thenReturn(response);
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);


        // when & then
        mockMvc.perform(get("/api/member/received")
                        .header(AUTH_HEADER, ACCESS_TOKEN))
                .andExpect(status().isOk());

        verify(memberService).getMemberReceivedLetters(memberId);
    }

    @Test
    @DisplayName("참여 및 받은 편지 수 조회")
    void getMemberLetterCountsTest() throws Exception {
        // given
        Long memberId = 1L;
        int participationLetterCount = 5;
        int receiveLetterCount = 5;
        MemberLetterCountResponse response = MemberLetterCountResponse.of(participationLetterCount, receiveLetterCount);
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);
        when(memberService.getMemberLetterCount(memberId)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/member/letter-counts")
                        .header(AUTH_HEADER, ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.receiveLetterCount").value(receiveLetterCount))
                .andExpect(jsonPath("$.data.participationLetterCount").value(participationLetterCount));

        verify(memberService).getMemberLetterCount(memberId);
    }

    @Test
    @DisplayName("재방문 여부 확인")
    void checkMemberAlreadyVisitTest() throws Exception {
        // given
        Long memberId = 1L;
        MemberAlreadyVisitResponse response = MemberAlreadyVisitResponse.from(true);
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);
        when(memberService.getMemberAlreadyVisitStatus(memberId)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/member/visit")
                        .header(AUTH_HEADER, ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isVisited").value(true));

        verify(memberService).getMemberAlreadyVisitStatus(memberId);
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawMemberByIdTest() throws Exception {
        // given
        Long memberId = 1L;
        MemberWithdrawRequest request = new MemberWithdrawRequest();
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);
        doNothing().when(memberService).withdrawMember(eq(memberId), any(MemberWithdrawRequest.class));

        // when & then
        mockMvc.perform(post("/api/member/withdraw")
                        .header(AUTH_HEADER, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(memberService).withdrawMember(eq(memberId), refEq(request));
    }

}
