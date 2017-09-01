package ys.prototype.fmtaq.query.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskQueryDTO {
    private final String id;
    private final String status;
    private final List<CommandQueryDTO> commands = new ArrayList<>();

    public TaskQueryDTO(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
