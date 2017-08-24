package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Sequence implements Task {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.REGISTERED;
    private UUID firstCommandId;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<LinkedCommand> commands;

    public Sequence(UUID id) {
        this.id = id;
    }

    @Override
    public Set<Command> getCommandsForStart() {
        Predicate<LinkedCommand> filter = linkedCommand -> linkedCommand.getId() == getFirstCommandId();
        return commands.stream().filter(filter).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
