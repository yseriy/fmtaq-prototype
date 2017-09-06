package ys.prototype.fmtaq.command.domain.task.group;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SyncTaskGroup extends TaskGroup {

    private String responseAddress;

    @Override
    public String getResponseAddress() {
        return responseAddress;
    }
}
