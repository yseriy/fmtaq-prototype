package ys.prototype.fmtaq.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.FmtaqErrorList;
import ys.prototype.fmtaq.domain.FmtaqException;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime startTimestamp;
    private LocalDateTime statusTimestamp;
    private String address;
    private String body;

    @Enumerated(EnumType.STRING)
    private CommandStatus commandStatus;

    @Version
    private Long version;

    @ManyToOne
    private Task task;

    protected Command(UUID id, String address, String body, CommandSender commandSender) {
        this.id = id;
        this.startTimestamp = this.statusTimestamp = LocalDateTime.now();
        this.address = address;
        this.body = body;
        this.commandStatus = CommandStatus.REGISTERED;
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
                throw new FmtaqException(FmtaqErrorList.UNKNOWN_COMMAND_RESPONSE_STATUS).set("response status",
                        responseStatus.toString());
        }
    }

    protected abstract void handleOkResponse();

    protected abstract void handleErrorResponse();

    protected void setCommandStatus(CommandStatus commandStatus) {
        this.statusTimestamp = LocalDateTime.now();
        this.commandStatus = commandStatus;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
