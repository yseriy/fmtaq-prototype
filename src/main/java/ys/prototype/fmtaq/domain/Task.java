package ys.prototype.fmtaq.domain;

import lombok.Data;

import java.util.UUID;

@Data
class Task {
    private UUID id;
    private Address address;
    private Body body;
}
