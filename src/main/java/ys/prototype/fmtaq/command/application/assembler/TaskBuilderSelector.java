package ys.prototype.fmtaq.command.application.assembler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.application.ApplicationErrorList;
import ys.prototype.fmtaq.command.domain.FmtaqException;
import ys.prototype.fmtaq.command.domain.paralleltask.ParallelTaskBuilder;
import ys.prototype.fmtaq.command.domain.sequencetask.SequenceTaskBuilder;
import ys.prototype.fmtaq.command.domain.singletask.SingleTaskBuilder;
import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.TaskBuilder;

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
                throw new FmtaqException(ApplicationErrorList.UNKNOWN_TASK_TYPE).set("task type", taskType);
        }
    }
}
