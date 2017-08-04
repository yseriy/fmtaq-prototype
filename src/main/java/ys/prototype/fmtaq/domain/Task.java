package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    public Task(TaskStatus status, TaskType type) {
        this.status = status;
        this.type = type;
    }

    public Boolean isErrorFatal() {
        return type == TaskType.SEQUENCE;
    }

    public void setStatusOk() {
        if (status != TaskStatus.PARTIAL) {
            status = TaskStatus.OK;
        }
    }

    public void setStatusError() {
        status = (type == TaskType.GROUP) ? TaskStatus.PARTIAL : TaskStatus.ERROR;
    }
}
