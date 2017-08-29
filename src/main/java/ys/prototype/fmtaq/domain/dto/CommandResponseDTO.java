package ys.prototype.fmtaq.domain.dto;

import lombok.Data;
import ys.prototype.fmtaq.domain.ResponseStatus;

import java.util.UUID;

@Data
public class CommandResponseDTO {
    private final UUID CommandId;
    private final ResponseStatus responseStatus;
    private final String body;

    public Boolean isStatusOk() {
        return responseStatus == ResponseStatus.OK;
    }
}
