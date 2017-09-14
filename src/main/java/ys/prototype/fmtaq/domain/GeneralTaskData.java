package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class GeneralTaskData {

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public GeneralTaskData(TaskStatus status) {
        setStatus(status);
    }
}
