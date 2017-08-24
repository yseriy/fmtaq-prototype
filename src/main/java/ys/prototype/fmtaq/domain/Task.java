package ys.prototype.fmtaq.domain;

import java.util.Set;
import java.util.UUID;

public interface Task {
    UUID getId();

    Set<Command> getCommandsForStart();
}
