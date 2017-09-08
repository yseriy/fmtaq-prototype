package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.CommandReturnStatus;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance
public abstract class Command {

    @Id
    private UUID id;
    private String address;
    private String body;

    @Enumerated(EnumType.STRING)
    private CommandStatus status = CommandStatus.REGISTERED;

    @Version
    private Long version;

    public abstract void updateCommandStatus(CommandReturnStatus commandReturnStatus);

    public abstract Boolean isLast();

    public abstract UUID getNextCommandId();

    public Command(UUID id, String address, String body) {
        setId(id);
        setAddress(address);
        setBody(body);
    }

    protected void setCommandStatus(CommandReturnStatus commandReturnStatus) {
        switch (commandReturnStatus) {
            case OK:
                setStatus(CommandStatus.OK);
                break;
            case ERROR:
                setStatus(CommandStatus.ERROR);
                break;
            default:
                throw new RuntimeException("unknown command response status: " + commandReturnStatus);
        }
    }
}
