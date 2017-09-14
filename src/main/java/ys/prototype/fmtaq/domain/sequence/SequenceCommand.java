package ys.prototype.fmtaq.domain.sequence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.GeneralCommandData;
import ys.prototype.fmtaq.domain.SequenceCommandData;
import ys.prototype.fmtaq.domain.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceCommand extends Command {

    private GeneralCommandData generalCommandData;
    private SequenceCommandData sequenceCommandData;

    public SequenceCommand(UUID id, Task task, GeneralCommandData generalCommandData,
                           SequenceCommandData sequenceCommandData) {
        super(id, task);
        setGeneralCommandData(generalCommandData);
        setSequenceCommandData(sequenceCommandData);
    }
}
