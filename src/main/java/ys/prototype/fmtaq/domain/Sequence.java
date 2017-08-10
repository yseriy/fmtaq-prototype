package ys.prototype.fmtaq.domain;

import javax.persistence.Entity;

@Entity
public class Sequence extends Task {

    public Sequence() {
    }

    public Sequence(Integer commandCount) {
        super(commandCount);
    }

    @Override
    Boolean hasNonFatalStatus() {
        return getStatus() != TaskStatus.ERROR;
    }

    @Override
    void setCommandSuccessStatus() {

    }

    @Override
    void setCommandErrorStatus() {
        setStatus(TaskStatus.ERROR);
    }

    @Override
    void setLastCommandSuccessStatus() {
        setStatus(TaskStatus.OK);
    }

    @Override
    void setLastCommandErrorStatus() {
        setStatus(TaskStatus.ERROR);
    }
}
