package com.ittory.socket.usecase;

import com.ittory.socket.dto.EndResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterEndUseCase {

    public EndResponse execute(Long letterId) {
        return EndResponse.from(letterId);
    }

}
