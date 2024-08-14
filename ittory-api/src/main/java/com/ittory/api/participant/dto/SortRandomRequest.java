package com.ittory.api.participant.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SortRandomRequest {
    private Long letterId;
    private List<Long> participantIds;
}
