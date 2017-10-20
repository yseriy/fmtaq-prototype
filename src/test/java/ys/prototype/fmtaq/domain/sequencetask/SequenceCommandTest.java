package ys.prototype.fmtaq.domain.sequencetask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandRepository;
import ys.prototype.fmtaq.domain.task.CommandSender;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceCommandTest {

    @MockBean
    private CommandSender commandSender;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommandRepository commandRepository;

    private UUID sequenceCommandId1;
    private SequenceTask sequenceTask;
    private SequenceCommand sequenceCommand1;
    private SequenceCommand sequenceCommand2;
    private SequenceCommand sequenceCommand3;
    private SequenceCommand sequenceCommand4;

    @Before
    public void setup() {
        UUID sequenceTaskId = UUID.randomUUID();
        UUID sequenceCommandId1 = UUID.randomUUID();
        UUID sequenceCommandId2 = UUID.randomUUID();
        UUID sequenceCommandId3 = UUID.randomUUID();
        UUID sequenceCommandId4 = UUID.randomUUID();
        String account = "account";
        String serviceType = "service_type";
        List<String> addressList = Arrays.asList("address_0", "address_1", "address_2", "address_3");
        List<String> bodyList = Arrays.asList("body_0", "body_1", "body_2", "body_3");

        SequenceTask sequenceTask = new SequenceTask(sequenceTaskId, account, serviceType, commandSender);
        SequenceCommand sequenceCommand1 = new SequenceCommand(sequenceCommandId1, addressList.get(0), bodyList.get(0),
                commandSender);
        SequenceCommand sequenceCommand2 = new SequenceCommand(sequenceCommandId2, addressList.get(1), bodyList.get(1),
                commandSender);
        SequenceCommand sequenceCommand3 = new SequenceCommand(sequenceCommandId3, addressList.get(2), bodyList.get(2),
                commandSender);
        SequenceCommand sequenceCommand4 = new SequenceCommand(sequenceCommandId4, addressList.get(3), bodyList.get(3),
                commandSender);

        this.sequenceCommandId1 = sequenceCommandId1;
        this.sequenceTask = sequenceTask;
        this.sequenceCommand1 = sequenceCommand1;
        this.sequenceCommand2 = sequenceCommand2;
        this.sequenceCommand3 = sequenceCommand3;
        this.sequenceCommand4 = sequenceCommand4;
    }

    @Test
    public void save() {
        loadTask();

        entityManager.persist(sequenceTask);
        entityManager.flush();
        entityManager.clear();

        Command commandFromDb = commandRepository.findOne(sequenceCommandId1);
        commandFromDb.setCommandSender(commandSender);
        assertThat(commandFromDb).isNotNull();
        assertThat(commandFromDb).isEqualTo(sequenceCommand1);
        assertThat(commandFromDb).isEqualToComparingFieldByField(sequenceCommand1);

        SequenceCommand sequenceCommand = (SequenceCommand) commandFromDb;
        assertThat(sequenceCommand.getNextCommand()).isEqualTo(sequenceCommand2);
    }

    @Test
    public void handleOkResponse() {
        loadTask();

        assertThat(sequenceCommand2.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(sequenceCommand2.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        sequenceCommand2.handleResponse(CommandResponseStatus.OK);

        assertThat(sequenceCommand2.getCommandStatus()).isEqualTo(CommandStatus.OK);
        assertThat(sequenceCommand2.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        verify(commandSender).send(sequenceCommand3);
    }

    @Test
    public void handleLastOkResponse() {
        loadTask();

        assertThat(sequenceCommand4.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(sequenceCommand4.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        sequenceCommand4.handleResponse(CommandResponseStatus.OK);

        assertThat(sequenceCommand4.getCommandStatus()).isEqualTo(CommandStatus.OK);
        assertThat(sequenceCommand4.getTask().getTaskStatus()).isEqualTo(TaskStatus.OK);

        verify(commandSender, never()).send(any());
    }

    @Test
    public void handleErrorResponse() {
        loadTask();
        testErrorResponseCommand(sequenceCommand2);
    }

    @Test
    public void handleLastErrorResponse() {
        loadTask();
        testErrorResponseCommand(sequenceCommand4);
    }

    private void loadTask() {
        Set<Command> commandSet = new HashSet<>();
        commandSet.add(sequenceCommand1);
        commandSet.add(sequenceCommand2);
        commandSet.add(sequenceCommand3);
        commandSet.add(sequenceCommand4);
        sequenceTask.setCommandSet(commandSet);
        sequenceTask.setFirstCommandId(sequenceCommandId1);

        sequenceCommand1.setNextCommand(sequenceCommand2);
        sequenceCommand2.setNextCommand(sequenceCommand3);
        sequenceCommand3.setNextCommand(sequenceCommand4);
        sequenceCommand4.setNextCommand(null);
    }

    private void testErrorResponseCommand(SequenceCommand sequenceCommand) {
        assertThat(sequenceCommand.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(sequenceCommand.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        sequenceCommand.handleResponse(CommandResponseStatus.ERROR);

        assertThat(sequenceCommand.getCommandStatus()).isEqualTo(CommandStatus.ERROR);
        assertThat(sequenceCommand.getTask().getTaskStatus()).isEqualTo(TaskStatus.ERROR);

        verify(commandSender, never()).send(any());
    }
}
