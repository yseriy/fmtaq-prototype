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
public class ParallelTask extends Task {

    private GeneralTaskData generalTaskData;
    private ParallelTaskData parallelTaskData;

    public ParallelTask(UUID id, GeneralTaskData generalTaskData, ParallelTaskData parallelTaskData) {
        super(id);
        setGeneralTaskData(generalTaskData);
        setParallelTaskData(parallelTaskData);
    }

    public Integer getCommandCounter() {
        return parallelTaskData.getCommandCounter();
    }
}
