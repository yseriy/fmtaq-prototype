package ys.prototype.fmtaq.query.dto;

import lombok.Data;

@Data
public class CommandQueryDTO {
    private String id;
    private String status;
    private String address;
    private String body;

    public CommandQueryDTO(String id, String status, String address, String body) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.body = body;
    }
}
