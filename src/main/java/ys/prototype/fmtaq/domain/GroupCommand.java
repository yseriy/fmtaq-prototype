package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupCommand extends Command {

    @ManyToOne
    private Group task;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
