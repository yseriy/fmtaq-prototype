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
@Inheritance
public abstract class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Integer commandCount;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Command> commands;

    abstract Boolean hasNonFatalStatus();

    abstract void setCommandSuccessStatus();

    abstract void setCommandErrorStatus();

    abstract void setLastCommandSuccessStatus();

    abstract void setLastCommandErrorStatus();

    Task(Integer commandCount) {
        this.status = TaskStatus.REGISTERED;
        this.commandCount = commandCount;
    }
}
