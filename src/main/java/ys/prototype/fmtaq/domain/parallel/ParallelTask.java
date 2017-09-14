package ys.prototype.fmtaq.domain.parallel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.TaskStatus;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelTask extends Task {

    private Integer commandCounter;

    public ParallelTask(UUID id, TaskStatus taskStatus, Integer commandCounter) {
        super(id, taskStatus);
        this.commandCounter = commandCounter;
    }
}
