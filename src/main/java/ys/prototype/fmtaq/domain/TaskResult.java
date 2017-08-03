package ys.prototype.fmtaq.domain;

import lombok.Data;

@Data
public class TaskResult {
    private Task task;
    private Boolean success;
    private Body body;

    public Boolean isTaskSuccessed() {
        return success;
    }
}
