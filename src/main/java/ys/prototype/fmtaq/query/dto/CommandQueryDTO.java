package ys.prototype.fmtaq.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandQueryDTO {
    private String id;
    private String status;
    private String address;
    private String body;
}
