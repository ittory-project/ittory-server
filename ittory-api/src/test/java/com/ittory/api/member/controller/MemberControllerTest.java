package com.ittory.api.member.controller;

import com.ittory.api.member.dto.MemberDetailResponse;
import com.ittory.api.member.dto.ParticipationResponse;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
