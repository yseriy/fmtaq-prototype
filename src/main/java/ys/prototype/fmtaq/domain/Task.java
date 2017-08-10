package ys.prototype.fmtaq.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "commands")
@EqualsAndHashCode(exclude = "commands")
@Entity
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    private Integer commandCount;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Command> commands;

    public Task(TaskStatus status, TaskType type, Integer commandCount) {
        this.status = status;
        this.type = type;
        this.commandCount = commandCount;
    }

    Boolean hasNonFatalStatus() {
        return getStatus() != TaskStatus.ERROR && getType() == TaskType.GROUP;
    }

    void setCommandSuccessStatus() {
    }

    void setCommandErrorStatus() {
        if (getType() == TaskType.GROUP) {

            if (getStatus() == TaskStatus.REGISTERED) {
                setStatus(TaskStatus.HAS_ERROR);
            }

        } else {
            setStatus(TaskStatus.ERROR);
        }
    }

    void setLastCommandSuccessStatus() {
        if (getType() == TaskType.GROUP) {

            if (getStatus() == TaskStatus.HAS_ERROR) {
                setStatus(TaskStatus.PARTIAL);
            } else {
                setStatus(TaskStatus.OK);
            }

        } else {
            setStatus(TaskStatus.OK);
        }
    }

    void setLastCommandErrorStatus() {
        if (getType() == TaskType.GROUP) {
            setStatus(TaskStatus.PARTIAL);
        } else {
            setStatus(TaskStatus.ERROR);
        }
    }
}
