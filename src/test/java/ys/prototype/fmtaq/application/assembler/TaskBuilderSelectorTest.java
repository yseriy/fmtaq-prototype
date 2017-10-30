package ys.prototype.fmtaq.application.assembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.ApplicationErrorList;
import ys.prototype.fmtaq.domain.FmtaqException;
import ys.prototype.fmtaq.domain.paralleltask.ParallelTaskBuilder;
import ys.prototype.fmtaq.domain.sequencetask.SequenceTaskBuilder;
import ys.prototype.fmtaq.domain.singletask.SingleTaskBuilder;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
public class TaskBuilderSelectorTest {

    @MockBean
    private CommandSender commandSender;

    @Test
    public void getTaskBuilderByTaskType() {
        String taskType = "SINGLE";
        TaskBuilderSelector taskBuilderSelector = new TaskBuilderSelector(commandSender);
        TaskBuilder taskBuilder = taskBuilderSelector.getTaskBuilderByTaskType(taskType);

        assertThat(taskBuilder).isNotNull();
        assertThat(taskBuilder).isInstanceOf(SingleTaskBuilder.class);

        taskType = "SEQUENCE";
        taskBuilderSelector = new TaskBuilderSelector(commandSender);
        taskBuilder = taskBuilderSelector.getTaskBuilderByTaskType(taskType);

        assertThat(taskBuilder).isNotNull();
        assertThat(taskBuilder).isInstanceOf(SequenceTaskBuilder.class);

        taskType = "PARALLEL";
        taskBuilderSelector = new TaskBuilderSelector(commandSender);
        taskBuilder = taskBuilderSelector.getTaskBuilderByTaskType(taskType);

        assertThat(taskBuilder).isNotNull();
        assertThat(taskBuilder).isInstanceOf(ParallelTaskBuilder.class);
    }

    @Test
    public void unknownTaskType() {
        String taskType = "TEST";
        TaskBuilderSelector taskBuilderSelector = new TaskBuilderSelector(commandSender);

        Throwable thrown = catchThrowable(() -> taskBuilderSelector.getTaskBuilderByTaskType(taskType));

        assertThat(thrown).isInstanceOf(FmtaqException.class);
        assertThat(thrown).hasFieldOrPropertyWithValue("fmtaqError", ApplicationErrorList.UNKNOWN_TASK_TYPE);
    }
}
