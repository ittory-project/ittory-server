package com.ittory.socket.mapper;

import com.ittory.domain.letter.dto.ElementEditData;
import com.ittory.socket.dto.ElementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElementConvertor {

    public ElementEditData toElementEditData(ElementRequest request) {
        return ElementEditData.of(request.getSequence(), request.getContent());
    }

}
