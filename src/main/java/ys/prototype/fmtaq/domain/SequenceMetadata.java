package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class SequenceMetadata {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Task task;

    @OneToMany(mappedBy = "sequenceMetadata", cascade = CascadeType.PERSIST)
    private Set<SequenceIndex> sequenceIndices = new HashSet<>();

    public void load(Task task, List<Command> commands) {
        loadIndex(commands);
        loadTask(task, commands);
    }

    private void loadIndex(List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            UUID commandId = commands.get(i).getId();
            UUID nextCommandId = (i == commands.size() - 1) ? null : commands.get(i + 1).getId();
            getSequenceIndices().add(new SequenceIndex(commandId, nextCommandId, this));
        }
    }

    private void loadTask(Task task, List<Command> commands) {
        task.loadCommands(new HashSet<>(commands));
        setTask(task);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
