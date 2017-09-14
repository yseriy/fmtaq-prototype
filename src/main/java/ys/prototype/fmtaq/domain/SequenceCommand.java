package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
