package com.ittory.socket.usecase;

import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.member.service.MemberDomainService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LetterWriteUseCaseTest {

    @Mock
    private ElementDomainService elementDomainService;

    @Mock
    private MemberDomainService memberDomainService;

    @InjectMocks
    private LetterWriteUseCase letterWriteUseCase;

//    @Test
//    void executeTest() {
//        //given
//        Member member = Member.create(1L, "test member", "image");
//        ElementRequest request = new ElementRequest(1L, "content");
//        LetterElement.create()
//
//        when(letterElementDomainService.changeContent(member, request.getElementId(), request.getContent()))
//                .thenReturn(member);
//        when()
//
//        //when
//        ElementResponse response = letterWriteUseCase.execute(member, request);
//
//        //then
//        Assertions.assertThat(response.getContent()).isEqualTo("content");
//        Assertions.assertThat(response.getElementId()).isEqualTo(1L);
//        Assertions.assertThat(response.getName()).isEqualTo("test member");
////        verify(letterElementDomainService).changeContent(request.getElementId(), request.getContent());
//
//    }


}
