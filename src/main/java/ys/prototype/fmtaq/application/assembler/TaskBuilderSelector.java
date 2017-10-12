package ys.prototype.fmtaq.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.paralleltask.ParallelTaskBuilder;
import ys.prototype.fmtaq.domain.sequencetask.SequenceTaskBuilder;
import ys.prototype.fmtaq.domain.singletask.SingleTaskBuilder;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.TaskBuilder;
import ys.prototype.fmtaq.exception.FmtaqErrorList;
import ys.prototype.fmtaq.exception.FmtaqException;

@Component
public class TaskBuilderSelector {

    private final CommandSender commandSender;

    public TaskBuilderSelector(@Qualifier(value = "amqpTransportCommandSender") CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    TaskBuilder getTaskBuilderByTaskType(String taskType) {
        switch (taskType) {
            case "SINGLE":
                return new SingleTaskBuilder(commandSender);
            case "SEQUENCE":
                return new SequenceTaskBuilder(commandSender);
            case "PARALLEL":
                return new ParallelTaskBuilder(commandSender);
            default:
                throw new FmtaqException(FmtaqErrorList.UNKNOWN_TASK_TYPE).set("task type", taskType);
        }
    }
}
