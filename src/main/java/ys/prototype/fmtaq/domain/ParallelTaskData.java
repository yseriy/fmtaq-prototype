package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ParallelTaskData {

    private Integer commandCounter;

    public ParallelTaskData(Integer commandCounter) {
        setCommandCounter(commandCounter);
    }
}
