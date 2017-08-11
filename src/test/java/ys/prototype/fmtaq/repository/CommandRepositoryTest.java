package ys.prototype.fmtaq.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.*;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CommandRepository commandRepository;

    private Task task;
    private Set<Command> commandSet;

    @Before
    public void setup() {
        Integer commandCounter = 10;
        String commandAddress = "test_address_";
        String commandBody = "test_body_";
        Set<Command> commandSet = new HashSet<>();

        Task task = new Group(commandCounter);

        for (int i = 0; i < commandCounter; i++) {
            commandSet.add(new Command(commandAddress + i, commandBody + i, i, task));
        }

        task.setCommands(commandSet);

        entityManager.persist(task);
        entityManager.flush();
        entityManager.clear();

        this.task = task;
        this.commandSet = commandSet;
    }

    @Test
    public void findOne() {
        final int step = 4;
        Command command = commandSet.stream().filter(e -> e.getStep() == step).findFirst().orElse(null);
        assertThat(command).isNotNull();

        Command commandFromDb = commandRepository.findOne(command.getId());

        assertThat(commandFromDb).isNotNull();
        assertThat(commandFromDb.getTask()).isNotNull();

        assertThat(commandFromDb.getTask().getId()).isEqualTo(task.getId());
        assertThat(commandFromDb.getTask().getStatus()).isEqualTo(task.getStatus());

        assertThat(commandFromDb.getAddress()).isEqualTo(command.getAddress());
        assertThat(commandFromDb.getBody()).isEqualTo(command.getBody());
        assertThat(commandFromDb.getStatus()).isEqualTo(command.getStatus());
        assertThat(commandFromDb.getStep()).isEqualTo(command.getStep());

        entityManager.flush();
    }

    @Test
    public void getNextCommand() {
        Integer commandCounter = commandSet.size();
        Task nonExistTask = new Sequence(3);

        entityManager.persist(nonExistTask);
        entityManager.flush();
        entityManager.clear();

        for (int i = 0; i < commandCounter; i++) {
            Command command = commandRepository.getNextCommand(nonExistTask, CommandStatus.REGISTERED, i);
            assertThat(command).isNull();
        }

        entityManager.clear();

        Task taskFromDb = entityManager.find(Task.class, task.getId());

        for (int i = 0; i < commandCounter; i++) {
            Command command = commandRepository.getNextCommand(taskFromDb, CommandStatus.REGISTERED, i);
            assertThat(command).isNotNull();
        }

        entityManager.clear();

        taskFromDb = entityManager.find(Task.class, task.getId());

        for (int i = 0; i < commandCounter; i++) {
            Command command = commandRepository.getNextCommand(taskFromDb, CommandStatus.ERROR, i);
            assertThat(command).isNull();
        }

        entityManager.clear();

        taskFromDb = entityManager.find(Task.class, task.getId());
        Command command = commandRepository.getNextCommand(taskFromDb, CommandStatus.REGISTERED, commandCounter + 1);
        assertThat(command).isNull();
    }
}
