package com.ittory.socket.usecase;

import com.ittory.socket.dto.StartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterStartUseCase {

    public StartResponse execute(Long letterId) {
        return StartResponse.from(letterId);
    }

}
