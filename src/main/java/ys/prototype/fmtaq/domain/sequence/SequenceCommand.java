package ys.prototype.fmtaq.domain.sequence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceCommand extends Command {

    private UUID nextCommandId;

    public SequenceCommand(UUID id, UUID nextCommandId, String address, String body, CommandStatus commandStatus,
                           Task task) {
        super(id, address, body, commandStatus, task);
        this.nextCommandId = nextCommandId;
    }
}
