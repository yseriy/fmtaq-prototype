package ys.prototype.fmtaq.domain.sequencecommand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.command.Task;
import ys.prototype.fmtaq.domain.TaskStatus;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceTask extends Task {

    public SequenceTask(UUID id, TaskStatus taskStatus) {
        super(id, taskStatus);
    }
}
