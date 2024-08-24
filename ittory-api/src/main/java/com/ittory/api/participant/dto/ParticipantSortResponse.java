package com.ittory.api.participant.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantSortResponse {

    private List<ParticipantProfile> participants;

    public static ParticipantSortResponse from(List<ParticipantProfile> participants) {
        return ParticipantSortResponse.builder()
                .participants(participants)
                .build();
    }

}
