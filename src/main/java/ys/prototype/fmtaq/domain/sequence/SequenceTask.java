package ys.prototype.fmtaq.domain.sequence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.GeneralTaskData;
import ys.prototype.fmtaq.domain.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceTask extends Task {

    private GeneralTaskData generalTaskData;

    public SequenceTask(UUID id, GeneralTaskData generalTaskData) {
        super(id);
        setGeneralTaskData(generalTaskData);
    }
}
