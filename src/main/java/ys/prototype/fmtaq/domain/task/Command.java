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
        this(id, address, body, commandStatus, commandSender);
        this.task = task;
    }

    public Command(UUID id, String address, String body, CommandStatus commandStatus, CommandSender commandSender) {
        setId(id);
        setAddress(address);
        setBody(body);
        setCommandStatus(commandStatus);
        setCommandSender(commandSender);
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
                throw new RuntimeException("unknown command response status: " + responseStatus);
        }
    }

    protected abstract void handleOkResponse();

    protected abstract void handleErrorResponse();
}
