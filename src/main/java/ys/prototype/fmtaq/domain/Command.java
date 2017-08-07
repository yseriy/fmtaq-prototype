package ys.prototype.fmtaq.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "task")
@EqualsAndHashCode(exclude = "task")
@Entity
public class Command {

    @Id
    @GeneratedValue
    private UUID id;

    private String address;

    private String body;

    @Enumerated(EnumType.STRING)
    private CommandStatus status;

    private Integer step;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Task task;

    public Command(String address, String body, CommandStatus status, Integer step) {
        this.address = address;
        this.body = body;
        this.status = status;
        this.step = step;
    }

    public Command(String address, String body, CommandStatus status, Integer step, Task task) {
        this(address, body, status, step);
        this.task = task;
    }

    public void setStatusOk() {
        status = CommandStatus.OK;
    }

    public void setStatusError() {
        status = CommandStatus.ERROR;
    }
}
