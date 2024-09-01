package com.ittory.api.participant.controller;

import com.ittory.api.participant.dto.ParticipantSortResponse;
import com.ittory.api.participant.dto.SortRandomRequest;
import com.ittory.api.participant.usecase.ParticipantSetSortUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantSetSortUseCase participantSortSetUseCase;

    @PostMapping("/random")
    public ResponseEntity<ParticipantSortResponse> setParticipantSortByRandom(@RequestBody SortRandomRequest request) {
        ParticipantSortResponse response = participantSortSetUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }
}
