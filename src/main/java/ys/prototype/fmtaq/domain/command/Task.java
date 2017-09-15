package ys.prototype.fmtaq.domain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.TaskStatus;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance
public abstract class Task {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Version
    private Long version;

    public Task(UUID id, TaskStatus taskStatus) {
        this.id = id;
        this.taskStatus = taskStatus;
    }
}
