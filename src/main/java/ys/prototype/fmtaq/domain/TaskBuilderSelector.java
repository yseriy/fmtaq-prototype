package ys.prototype.fmtaq.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.paralleltask.ParallelTaskBuilder;
import ys.prototype.fmtaq.domain.sequencetask.SequenceTaskBuilder;
import ys.prototype.fmtaq.domain.singletask.SingleTaskBuilder;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

@Component
public class TaskBuilderSelector {

    private final CommandSender commandSender;

    public TaskBuilderSelector(@Qualifier(value = "commandAmqpSender") CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public TaskBuilder getTaskBuilderByTaskType(String taskType) {
        switch (taskType) {
            case "SINGLE":
                return new SingleTaskBuilder(commandSender);
            case "SEQUENCE":
                return new SequenceTaskBuilder(commandSender);
            case "PARALLEL":
                return new ParallelTaskBuilder(commandSender);
            default:
                throw unknownTaskType(taskType);
        }
    }

    private RuntimeException unknownTaskType(String taskType) {
        String exceptionString = String.format("unknown task type: %s", taskType);
        return new RuntimeException(exceptionString);
    }
}
