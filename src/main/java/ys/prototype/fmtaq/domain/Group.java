package ys.prototype.fmtaq.domain;

import javax.persistence.Entity;

@Entity
public class Group extends Task {

    public Group() {
        super();
    }

    public Group(Integer commandCount) {
        super(commandCount);
    }

    @Override
    Boolean hasNonFatalStatus() {
        return true;
    }

    @Override
    void setCommandSuccessStatus() {

    }

    @Override
    void setCommandErrorStatus() {
        if (getStatus() == TaskStatus.REGISTERED) {
            setStatus(TaskStatus.HAS_ERROR);
        }
    }

    @Override
    void setLastCommandSuccessStatus() {
        if (getStatus() == TaskStatus.HAS_ERROR) {
            setStatus(TaskStatus.PARTIAL);
        } else {
            setStatus(TaskStatus.OK);
        }
    }

    @Override
    void setLastCommandErrorStatus() {
        setStatus(TaskStatus.PARTIAL);
    }
}
