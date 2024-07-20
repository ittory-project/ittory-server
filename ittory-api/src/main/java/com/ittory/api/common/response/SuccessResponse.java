package com.ittory.api.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"success", "status", "data"})
public class SuccessResponse {

    private final boolean success = true;
    private final int status;
    private final Object data;

    public SuccessResponse(int status, Object data) {
        this.status = status;
        this.data = data;
    }

}
