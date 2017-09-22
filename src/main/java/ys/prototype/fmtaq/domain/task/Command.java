package ys.prototype.fmtaq.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.domain.CommandStatus;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance
public abstract class Command {

    @Transient
    private CommandSender commandSender;

    @Id
    private UUID id;
    private String address;
    private String body;

    @Enumerated(EnumType.STRING)
    private CommandStatus commandStatus;

    @Version
    private Long version;

    @ManyToOne
    private Task task;

    public Command(UUID id, String address, String body, CommandStatus commandStatus, Task task,
                   CommandSender commandSender) {
        this.id = id;
        this.address = address;
        this.body = body;
        this.commandStatus = commandStatus;
        this.task = task;
        this.commandSender = commandSender;
    }

    public void handleResponse(CommandResponseStatus responseStatus) {
        switch (responseStatus) {
            case OK:
                handleOkResponse();
                break;
            case ERROR:
                handleErrorResponse();
                break;
            default:
                throw new RuntimeException("unknown commandResponse status: " + responseStatus);
        }
    }

    protected abstract void handleOkResponse();

    protected abstract void handleErrorResponse();
}
