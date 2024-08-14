package com.ittory.api.participant.dto;

import com.ittory.api.member.dto.MemberLetterProfile;
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

    private List<MemberLetterProfile> participants;

    public static ParticipantSortResponse of(List<MemberLetterProfile> participants) {
        return ParticipantSortResponse.builder()
                .participants(participants)
                .build();
    }

}
