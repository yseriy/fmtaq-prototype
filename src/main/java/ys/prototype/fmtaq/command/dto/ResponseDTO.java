package ys.prototype.fmtaq.command.dto;

import lombok.Data;
import ys.prototype.fmtaq.command.domain.CommandReturnStatus;

import java.util.UUID;

@Data
public class ResponseDTO {
    private final UUID commandId;
    private final CommandReturnStatus commandReturnStatus;
    private final String body;
}
