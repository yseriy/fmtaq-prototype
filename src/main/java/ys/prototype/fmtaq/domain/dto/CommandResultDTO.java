package ys.prototype.fmtaq.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CommandResultDTO {
    private UUID commandId;
    private Boolean statusOk;
    private String body;

    public Boolean isStatusOk() {
        return statusOk;
    }
}
