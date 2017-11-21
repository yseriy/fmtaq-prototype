package ys.prototype.fmtaq.command.domain.singletask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.domain.CommandResponseStatus;
import ys.prototype.fmtaq.command.domain.CommandStatus;
import ys.prototype.fmtaq.command.domain.TaskStatus;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandRepository;
import ys.prototype.fmtaq.command.domain.task.CommandSender;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SingleCommandTest {

    @MockBean
    private CommandSender commandSender;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommandRepository commandRepository;

    private UUID singleCommandId;
    private SingleTask singleTask;
    private SingleCommand singleCommand;

    @Before
    public void setup() {
        UUID singleTaskId = UUID.randomUUID();
        UUID singleCommandId = UUID.randomUUID();
        String account = "account";
        String serviceType = "service_type";
        String address = "address";
        String body = "body";

        SingleTask singleTask = new SingleTask(singleTaskId, account, serviceType, commandSender);
        SingleCommand singleCommand = new SingleCommand(singleCommandId, address, body, commandSender);

        this.singleCommandId = singleCommandId;
        this.singleTask = singleTask;
        this.singleCommand = singleCommand;
    }

    @Test
    public void save() {
        singleCommand.setTask(singleTask);

        entityManager.persist(singleTask);
        commandRepository.save(singleCommand);
        entityManager.flush();
        entityManager.clear();

        Command singeCommandFromDb = commandRepository.findOne(singleCommandId);
        singeCommandFromDb.setCommandSender(commandSender);

        assertThat(singeCommandFromDb).isNotNull();
        assertThat(singeCommandFromDb).isInstanceOf(SingleCommand.class);
        assertThat(singeCommandFromDb).isEqualToComparingFieldByField(singleCommand);
    }

    @Test
    public void handleOkResponse() {
        singleCommand.setTask(singleTask);

        assertThat(singleCommand.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(singleCommand.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        singleCommand.handleResponse(CommandResponseStatus.OK);

        assertThat(singleCommand.getCommandStatus()).isEqualTo(CommandStatus.OK);
        assertThat(singleCommand.getTask().getTaskStatus()).isEqualTo(TaskStatus.OK);
    }

    @Test
    public void handleErrorResponse() {
        singleCommand.setTask(singleTask);

        assertThat(singleCommand.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(singleCommand.getTask().getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        singleCommand.handleResponse(CommandResponseStatus.ERROR);

        assertThat(singleCommand.getCommandStatus()).isEqualTo(CommandStatus.ERROR);
        assertThat(singleCommand.getTask().getTaskStatus()).isEqualTo(TaskStatus.ERROR);
    }
}
