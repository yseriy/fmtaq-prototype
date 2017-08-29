package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Sequence extends Task {

    private UUID firstCommandId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    private Set<LinkedCommand> commands;

    public void loadCommands(List<Command> commands) {
        setCommands(load(commands));

    }

    private Set<LinkedCommand> load(List<Command> commands) {
        return commands.stream().map(this::loadOne).collect(Collectors.toSet());
    }

    private LinkedCommand loadOne(Command command) {
        return new LinkedCommand(command.getId(), command.getAddress(), command.getBody(), this);
    }

    public Sequence(UUID id) {
        super(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
