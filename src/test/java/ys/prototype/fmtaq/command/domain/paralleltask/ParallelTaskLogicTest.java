package ys.prototype.fmtaq.command.domain.paralleltask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.domain.FmtaqErrorList;
import ys.prototype.fmtaq.command.domain.FmtaqException;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ParallelTaskLogicTest {

    @MockBean
    private CommandSender commandSender;

    private Task task;
    private Command command1;
    private Command command2;
    private Command command3;
    private List<String> addressList;
    private List<String> bodyList;

    @Before
    public void setup() {
        UUID taskId = UUID.randomUUID();
        UUID commandId1 = UUID.randomUUID();
        UUID commandId2 = UUID.randomUUID();
        UUID commandId3 = UUID.randomUUID();

        String account = "account";
        String serviceType = "service_type";
        List<String> addressList = Arrays.asList("address_0", "address_1", "address_2");
        List<String> bodyList = Arrays.asList("body_0", "body_1", "body_2");

        Task task = new ParallelTask(taskId, account, serviceType, commandSender);
        Command command1 = new ParallelCommand(commandId1, addressList.get(0), bodyList.get(0), commandSender);
        Command command2 = new ParallelCommand(commandId2, addressList.get(1), bodyList.get(1), commandSender);
        Command command3 = new ParallelCommand(commandId3, addressList.get(2), bodyList.get(2), commandSender);

        this.task = task;
        this.command1 = command1;
        this.command2 = command2;
        this.command3 = command3;
        this.addressList = addressList;
        this.bodyList = bodyList;
    }

    @Test
    public void start() {
        Set<Command> commandSet = new HashSet<>();
        commandSet.add(command1);
        commandSet.add(command2);
        commandSet.add(command3);
        task.setCommandSet(commandSet);

        task.start();

        verify(commandSender).send(command1);
        verify(commandSender).send(command2);
        verify(commandSender).send(command3);
    }

    @Test
    public void loadCommandList() {
        UUID taskId = UUID.randomUUID();
        UUID commandId1 = UUID.randomUUID();
        UUID commandId2 = UUID.randomUUID();
        UUID commandId3 = UUID.randomUUID();

        String account = "account";
        String serviceType = "service_type";

        ParallelTask task = new ParallelTask(taskId, account, serviceType, commandSender);
        ParallelCommand command1 = new ParallelCommand(commandId1, addressList.get(0), bodyList.get(0), commandSender);
        ParallelCommand command2 = new ParallelCommand(commandId2, addressList.get(1), bodyList.get(1), commandSender);
        ParallelCommand command3 = new ParallelCommand(commandId3, addressList.get(2), bodyList.get(2), commandSender);

        List<ParallelCommand> parallelCommandList = new ArrayList<>();
        parallelCommandList.add(command1);
        parallelCommandList.add(command2);
        parallelCommandList.add(command3);

        task.loadCommandList(parallelCommandList);

        assertThat(task.getCommandCounter()).isEqualTo(parallelCommandList.size());
        assertThat(task.getCommandSet()).containsExactlyInAnyOrder(command1, command2, command3);
    }

    @Test
    public void reduceCommandCounter() {
        ParallelTask parallelTask = (ParallelTask) task;
        parallelTask.setCommandCounter(1);

        parallelTask.reduceCommandCounter();

        assertThat(parallelTask.getCommandCounter()).isEqualTo(0);
    }

    @Test
    public void illegalCommandCounterValueException() {
        ParallelTask parallelTask = (ParallelTask) task;

        Throwable thrown = catchThrowable(parallelTask::reduceCommandCounter);

        assertThat(thrown).isInstanceOf(FmtaqException.class);
        assertThat(thrown).hasFieldOrPropertyWithValue("fmtaqError", FmtaqErrorList.ILLEGAL_COMMAND_COUNTER_VALUE);
    }
}
