package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.ResponseStatus;

import javax.persistence.*;
import java.util.Set;
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
    private TaskStatus status = TaskStatus.REGISTERED;

    @Version
    private Long version;

    public Task(UUID id) {
        setId(id);
    }

    public abstract Set<Command> getStartCommands();

    public abstract String getResponseAddress();

    public abstract void setTaskEndStatus(ResponseStatus responseStatus);
}
