package ys.prototype.fmtaq.domain.paralleltask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ParallelCommandLogicTest {

    @MockBean
    private CommandSender commandSender;

    private Task task;
    private Command command1;

    @Before
    public void setup() {
        UUID taskId = UUID.randomUUID();
        UUID commandId1 = UUID.randomUUID();
        UUID commandId2 = UUID.randomUUID();
        UUID commandId3 = UUID.randomUUID();

        String account = "account";
        String serviceType = "service_type";
        Integer commandCounter = 3;
        List<String> addressList = Arrays.asList("address_0", "address_1", "address_2");
        List<String> bodyList = Arrays.asList("body_0", "body_1", "body_2");

        Task task = new ParallelTask(taskId, account, serviceType, commandSender);
        Command command1 = new ParallelCommand(commandId1, addressList.get(0), bodyList.get(0), commandSender);
        Command command2 = new ParallelCommand(commandId2, addressList.get(1), bodyList.get(1), commandSender);
        Command command3 = new ParallelCommand(commandId3, addressList.get(2), bodyList.get(2), commandSender);

        ParallelTask parallelTask = (ParallelTask) task;
        parallelTask.setCommandCounter(commandCounter);

        Set<Command> commandSet = new HashSet<>();
        commandSet.add(command1);
        commandSet.add(command2);
        commandSet.add(command3);
        task.setCommandSet(commandSet);

        this.task = task;
        this.command1 = command1;
    }

    @Test
    public void handleOkResponse() {
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        ParallelTask parallelTask = (ParallelTask) task;
        assertThat(parallelTask.getCommandCounter()).isEqualTo(3);

        command1.handleResponse(CommandResponseStatus.OK);

        assertThat(parallelTask.getCommandCounter()).isEqualTo(2);
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.OK);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);
    }

    @Test
    public void handleLastOkResponse() {
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        ParallelTask parallelTask = (ParallelTask) task;
        parallelTask.setCommandCounter(1);

        command1.handleResponse(CommandResponseStatus.OK);

        assertThat(parallelTask.getCommandCounter()).isEqualTo(0);
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.OK);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.OK);
    }

    @Test
    public void handleErrorResponse() {
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        ParallelTask parallelTask = (ParallelTask) task;
        parallelTask.setCommandCounter(2);

        command1.handleResponse(CommandResponseStatus.ERROR);

        assertThat(parallelTask.getCommandCounter()).isEqualTo(1);
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.ERROR);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);
    }

    @Test
    public void handleLastErrorResponse() {
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        ParallelTask parallelTask = (ParallelTask) task;
        parallelTask.setCommandCounter(1);

        command1.handleResponse(CommandResponseStatus.ERROR);

        assertThat(parallelTask.getCommandCounter()).isEqualTo(0);
        assertThat(command1.getCommandStatus()).isEqualTo(CommandStatus.ERROR);
        assertThat(command1.getTask().getTaskStatus()).isEqualTo(TaskStatus.ERROR);
    }
}
