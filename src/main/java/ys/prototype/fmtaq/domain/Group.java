package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Group extends Task {

    private Integer commandCounter;

    @OneToMany(mappedBy = "task")
    private Set<GroupCommand> commands;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
