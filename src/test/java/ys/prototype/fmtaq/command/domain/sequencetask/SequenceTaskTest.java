package ys.prototype.fmtaq.command.domain.sequencetask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;
import ys.prototype.fmtaq.command.domain.task.TaskRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceTaskTest {

    @MockBean
    private CommandSender commandSender;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private UUID sequenceTaskId;
    private UUID sequenceCommandId1;
    private SequenceTask sequenceTask;
    private SequenceCommand sequenceCommand1;
    private SequenceCommand sequenceCommand2;
    private SequenceCommand sequenceCommand3;

    @Before
    public void setup() {
        UUID sequenceTaskId = UUID.randomUUID();
        UUID sequenceCommandId1 = UUID.randomUUID();
        UUID sequenceCommandId2 = UUID.randomUUID();
        UUID sequenceCommandId3 = UUID.randomUUID();
        String account = "account";
        String serviceType = "service_type";
        List<String> addressList = Arrays.asList("address_0", "address_1", "address_2");
        List<String> bodyList = Arrays.asList("body_0", "body_1", "body_2");

        SequenceTask sequenceTask = new SequenceTask(sequenceTaskId, account, serviceType, commandSender);
        SequenceCommand sequenceCommand1 = new SequenceCommand(sequenceCommandId1, addressList.get(0), bodyList.get(0),
                commandSender);
        SequenceCommand sequenceCommand2 = new SequenceCommand(sequenceCommandId2, addressList.get(1), bodyList.get(1),
                commandSender);
        SequenceCommand sequenceCommand3 = new SequenceCommand(sequenceCommandId3, addressList.get(2), bodyList.get(2),
                commandSender);

        this.sequenceTaskId = sequenceTaskId;
        this.sequenceCommandId1 = sequenceCommandId1;
        this.sequenceTask = sequenceTask;
        this.sequenceCommand1 = sequenceCommand1;
        this.sequenceCommand2 = sequenceCommand2;
        this.sequenceCommand3 = sequenceCommand3;
    }

    @Test
    public void save() {
        Set<Command> commandSet = new HashSet<>();
        commandSet.add(sequenceCommand1);
        commandSet.add(sequenceCommand2);
        commandSet.add(sequenceCommand3);
        sequenceTask.setCommandSet(commandSet);
        sequenceTask.setFirstCommandId(sequenceCommandId1);

        sequenceCommand1.setNextCommand(sequenceCommand2);
        sequenceCommand2.setNextCommand(sequenceCommand3);
        sequenceCommand3.setNextCommand(null);

        taskRepository.save(sequenceTask);
        entityManager.flush();
        entityManager.clear();

        Task taskFromDb = taskRepository.findOne(sequenceTaskId);
        taskFromDb.setCommandSender(commandSender);

        assertThat(taskFromDb).isNotNull();
        assertThat(taskFromDb).isInstanceOf(SequenceTask.class);
        assertThat(taskFromDb.getAccount()).isEqualTo(sequenceTask.getAccount());
        assertThat(taskFromDb.getServiceType()).isEqualTo(sequenceTask.getServiceType());
        SequenceTask sequenceTaskFromDb = (SequenceTask) taskFromDb;
        assertThat(sequenceTaskFromDb.getFirstCommandId()).isEqualTo(sequenceCommandId1);
        assertThat(taskFromDb.getCommandSet()).containsExactlyInAnyOrder(sequenceCommand1, sequenceCommand2,
                sequenceCommand3);
    }

    @Test
    public void start() {
        Set<Command> commandSet = new HashSet<>();
        commandSet.add(sequenceCommand1);
        commandSet.add(sequenceCommand2);
        commandSet.add(sequenceCommand3);
        sequenceTask.setCommandSet(commandSet);
        sequenceTask.setFirstCommandId(sequenceCommandId1);

        sequenceCommand1.setNextCommand(sequenceCommand2);
        sequenceCommand2.setNextCommand(sequenceCommand3);
        sequenceCommand3.setNextCommand(null);

        sequenceTask.start();

        verify(commandSender).send(sequenceCommand1);
    }

    @Test
    public void loadCommandList() {
        List<SequenceCommand> sequenceCommandList = new ArrayList<>();
        sequenceCommandList.add(sequenceCommand1);
        sequenceCommandList.add(sequenceCommand2);
        sequenceCommandList.add(sequenceCommand3);

        sequenceTask.loadCommandList(sequenceCommandList);

        assertThat(sequenceTask.getFirstCommandId()).isEqualTo(sequenceCommandId1);
        assertThat(sequenceTask.getCommandSet()).containsExactlyInAnyOrder(sequenceCommand1, sequenceCommand2,
                sequenceCommand3);

        assertThat(sequenceCommand1.getNextCommand()).isEqualTo(sequenceCommand2);
        assertThat(sequenceCommand2.getNextCommand()).isEqualTo(sequenceCommand3);
        assertThat(sequenceCommand3.getNextCommand()).isNull();
    }
}
