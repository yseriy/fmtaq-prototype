package ys.prototype.fmtaq.command.dto;

import lombok.Data;
import ys.prototype.fmtaq.command.domain.ResponseStatus;

import java.util.UUID;

@Data
public class ResponseDTO {
    private final UUID commandId;
    private final ResponseStatus responseStatus;
    private final String body;
}
