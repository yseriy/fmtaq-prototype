package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Group implements Task {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.REGISTERED;
    private Integer commandCounter;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<GroupCommand> commands;

    public Group(UUID id, Integer commandCounter) {
        this.id = id;
        this.commandCounter = commandCounter;
    }

    @Override
    public Set<Command> getCommandsForStart() {
        return new HashSet<>(getCommands());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
