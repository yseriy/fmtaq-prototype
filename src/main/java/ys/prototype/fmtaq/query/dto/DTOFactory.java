package ys.prototype.fmtaq.query.dto;

import org.springframework.stereotype.Component;

@Component
public class DTOFactory {

    public TaskQueryDTO createTaskQueryDTO(String id, String status) {
        return new TaskQueryDTO(id, status);
    }

    public CommandQueryDTO createCommandQueryDTO(String id, String status, String address, String body) {
        return new CommandQueryDTO(id, status, address, body);
    }
}
