package com.ittory.api.member.controller;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @MockBean
    private LetterBoxService letterBoxService;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("참여자 편지함에서 편지 삭제")
    @Test
    public void deleteLetterInLetterBoxTest() throws Exception {
        //given
        String ACCESS_TOKEN = "access_token";
        Long letterId = 1L;
        AccessTokenInfo memberTokenInfo = AccessTokenInfo.of("1", "MEMBER");

        //when
        when(jwtProvider.resolveToken(ACCESS_TOKEN)).thenReturn(memberTokenInfo);

        //then
        mockMvc.perform(delete("/api/member/{letterId}", letterId)
                        .header("Authorization", ACCESS_TOKEN))
                .andExpect(status().isOk());

        verify(letterBoxService).deleteLetterInLetterBox(1L, letterId);


    }
}
