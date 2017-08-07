package ys.prototype.fmtaq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = "spring.jpa.show-sql=true")
public class CommandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommandRepository commandRepository;

    @Test
    public void save() {
        TaskStatus taskStatus = TaskStatus.REGISTERED;
        TaskType taskType = TaskType.GROUP;
        String commandAddress = "test_address_";
        String commandBody = "test_body_";
        CommandStatus status = CommandStatus.REGISTERED;

        Task task = new Task(taskStatus, taskType);
        Set<Command> commandSet = new HashSet<>();

        for (Integer i = 0; i < 10; i++) {
            commandSet.add(new Command(commandAddress + i, commandBody + i, status, i, task));
        }

        commandRepository.save(commandSet);
        entityManager.flush();
        entityManager.clear();

        task = entityManager.find(Task.class, task.getId());

        assertThat(task.getStatus()).isEqualTo(taskStatus);
        assertThat(task.getType()).isEqualTo(taskType);

        for (Command command : commandSet) {
            Command commandFromDb = entityManager.find(Command.class, command.getId());
            assertThat(commandFromDb.getAddress()).isEqualTo(command.getAddress());
            assertThat(commandFromDb.getBody()).isEqualTo(command.getBody());
            assertThat(commandFromDb.getStatus()).isEqualTo(command.getStatus());
            assertThat(commandFromDb.getStep()).isEqualTo(command.getStep());
            assertThat(commandFromDb.getTask()).isEqualTo(command.getTask());
        }
    }

    @Test
    public void getNextCommand() {
        TaskStatus taskStatus = TaskStatus.REGISTERED;
        TaskType taskType = TaskType.GROUP;
        String commandAddress = "test_address_";
        String commandBody = "test_body_";
        CommandStatus status = CommandStatus.REGISTERED;

        Task task = new Task(taskStatus, taskType);
        Set<Command> commandSet = new HashSet<>();

        Command command0 = new Command(commandAddress + 0, commandBody + 0, status, 0, task);
        commandSet.add(command0);

        for (Integer i = 1; i < 10; i++) {
            commandSet.add(new Command(commandAddress + i, commandBody + i, status, i, task));
        }

        commandRepository.save(commandSet);
        entityManager.flush();
        entityManager.clear();

        List<Command> nextCommandList = commandRepository.getNextCommand(task, status, new PageRequest(0, 1));

        assertThat(nextCommandList).isNotNull();
        assertThat(nextCommandList.get(0).getStep()).isEqualTo(0);

        entityManager.flush();
        entityManager.clear();

        Command command0FromDb = commandRepository.findOne(command0.getId());
        command0FromDb.setStatusOk();
        nextCommandList = commandRepository.getNextCommand(command0FromDb.getTask(), status, new PageRequest(0, 1));

        assertThat(nextCommandList).isNotNull();
        assertThat(nextCommandList.get(0).getStep()).isEqualTo(1);
    }
}
