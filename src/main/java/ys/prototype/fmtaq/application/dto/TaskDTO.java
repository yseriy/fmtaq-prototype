package ys.prototype.fmtaq.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDTO {

    private final String type;
    private final String account;
    private final String serviceType;
    private final List<CommandDTO> commandList;
}
