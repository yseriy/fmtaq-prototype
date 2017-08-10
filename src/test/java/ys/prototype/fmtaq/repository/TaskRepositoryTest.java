package ys.prototype.fmtaq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.Sequence;
import ys.prototype.fmtaq.domain.Task;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void save() {
        String commandAddress = "test_address_";
        String commandBody = "test_body_";
        Integer commandCounter = 10;

        Task task = new Sequence(commandCounter);
        Set<Command> commandSet = new HashSet<>();

        for (Integer i = 0; i < commandCounter; i++) {
            commandSet.add(new Command(commandAddress + i, commandBody + i, i, task));
        }

        task.setCommands(commandSet);

        taskRepository.save(task);
        entityManager.flush();
        entityManager.clear();

        Task taskFromDb = entityManager.find(Task.class, task.getId());
        assertThat(taskFromDb).isNotNull();
        assertThat(taskFromDb.getCommands()).isNotNull();
        assertThat(taskFromDb.getCommands().size()).isEqualTo(commandCounter);

        for (Command commandFromDb : taskFromDb.getCommands()) {
            assertThat(commandFromDb).isNotNull();
            assertThat(commandFromDb.getTask()).isNotNull();
        }
    }
}
