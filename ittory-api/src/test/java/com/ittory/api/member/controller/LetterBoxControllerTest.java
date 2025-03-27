package com.ittory.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ittory.api.member.dto.LetterBoxParticipationRequest;
import com.ittory.api.member.service.LetterBoxService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LetterBoxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LetterBoxService letterBoxService;

    @MockBean
    private JwtProvider jwtProvider;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ACCESS_TOKEN = "access_token";


    @DisplayName("참여자 편지함에 편지 저장")
    @Test
    public void saveInParticipationLetterBoxTest() throws Exception {
        //given
        LetterBoxParticipationRequest request = new LetterBoxParticipationRequest(1L);
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");

        //when
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);
        doNothing().when(letterBoxService).saveInParticipationLetterBox(any(LetterBoxParticipationRequest.class));

        //then
        mockMvc.perform(post("/api/letter-box/participation")
                        .header("Authorization", ACCESS_TOKEN)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(letterBoxService).saveInParticipationLetterBox(refEq(request));


    }
}
