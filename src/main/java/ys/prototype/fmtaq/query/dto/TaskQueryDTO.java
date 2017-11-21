package ys.prototype.fmtaq.query.dto;

import lombok.Data;

@Data
public class TaskQueryDTO {

    private final String id;
    private final String status;
    private final String startTimestamp;
    private final String statusTimestamp;
    private final String commandBody;
}
