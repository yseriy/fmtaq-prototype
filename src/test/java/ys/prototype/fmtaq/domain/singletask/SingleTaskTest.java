package ys.prototype.fmtaq.domain.singletask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.FmtaqErrorList;
import ys.prototype.fmtaq.domain.FmtaqException;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SingleTaskTest {

    @MockBean
    private CommandSender commandSender;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private UUID singleTaskId;
    private SingleTask singleTask;
    private SingleCommand singleCommand;
    private Set<Command> singleCommandSet;

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
        Set<Command> singleCommandSet = new HashSet<>();
        singleCommandSet.add(singleCommand);

        this.singleTaskId = singleTaskId;
        this.singleTask = singleTask;
        this.singleCommand = singleCommand;
        this.singleCommandSet = singleCommandSet;
    }

    @Test
    public void save() {
        singleTask.setCommandSet(singleCommandSet);

        taskRepository.save(singleTask);
        entityManager.flush();
        entityManager.clear();

        Task singleTaskFromDb = taskRepository.findOne(singleTaskId);
        singleTaskFromDb.setCommandSender(commandSender);

        assertThat(singleTaskFromDb).isNotNull();
        assertThat(singleTaskFromDb).isInstanceOf(SingleTask.class);
        assertThat(singleTaskFromDb).isEqualToComparingFieldByField(singleTask);
    }

    @Test
    public void start() {
        singleTask.setCommandSet(singleCommandSet);

        singleTask.start();

        verify(commandSender).send(singleCommand);
    }

    @Test
    public void loadCommandList() {
        List<SingleCommand> singleCommandList = new ArrayList<>();
        singleCommandList.add(singleCommand);

        singleTask.loadCommandList(singleCommandList);

        assertThat(singleTask.getCommandSet()).containsExactly(singleCommand);
    }

    @Test
    public void emptyCommandSetException() {
        singleTask.setCommandSet(new HashSet<>());

        Throwable thrown = catchThrowable(singleTask::start);

        assertThat(thrown).isInstanceOf(FmtaqException.class);
        assertThat(thrown).hasFieldOrPropertyWithValue("fmtaqError", FmtaqErrorList.EMPTY_COMMAND_SET);
    }
}
