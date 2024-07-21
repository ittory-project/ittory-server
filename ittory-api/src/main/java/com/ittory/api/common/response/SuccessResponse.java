package com.ittory.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
