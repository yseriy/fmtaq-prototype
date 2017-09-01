package ys.prototype.fmtaq.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskQueryDTO {
    private String id;
    private String status;
    private List<CommandQueryDTO> commands;

    public TaskQueryDTO(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
