package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LinkedCommand extends Command {

    private UUID nextCommandId;

    @ManyToOne
    private TaskSequence task;

    public LinkedCommand(UUID id, UUID nextCommandId, String address, String body) {
        super(id, address, body);
        setNextCommandId(nextCommandId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
