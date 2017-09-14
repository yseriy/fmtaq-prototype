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
public class ParallelCommand extends Command {

    private GeneralCommandData generalCommandData;

    public ParallelCommand(UUID id, Task task, GeneralCommandData generalCommandData) {
        super(id, task);
        setGeneralCommandData(generalCommandData);
    }
}
