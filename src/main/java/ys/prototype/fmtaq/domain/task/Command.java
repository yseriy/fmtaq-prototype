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
import java.time.ZoneId;
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
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("UTC+3"));

        setId(id);
        setStartTimestamp(localDateTime);
        setStatusTimestamp(localDateTime);
        setAddress(address);
        setBody(body);
        setCommandStatus(CommandStatus.REGISTERED);
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
                throw new FmtaqException(FmtaqErrorList.UNKNOWN_COMMAND_RESPONSE_STATUS).set("response status",
                        responseStatus.toString());
        }
    }

    protected abstract void handleOkResponse();

    protected abstract void handleErrorResponse();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
