package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.ResponseStatus;

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

    Command(UUID id, String address, String body) {
        setId(id);
        setAddress(address);
        setBody(body);
    }

    void setCommandStatus(ResponseStatus responseStatus) {
        switch (responseStatus) {
            case OK:
                setStatus(CommandStatus.OK);
                break;
            case ERROR:
                setStatus(CommandStatus.ERROR);
                break;
            default:
                throw new RuntimeException("unknown command response status: " + responseStatus);
        }
    }

    public abstract UUID updateStatusAndGetNextCommandId(ResponseStatus responseStatus);
}
