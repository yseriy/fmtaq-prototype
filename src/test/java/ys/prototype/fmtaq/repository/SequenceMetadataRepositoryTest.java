package ys.prototype.fmtaq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.*;
import ys.prototype.fmtaq.service.SequenceMetadataService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceMetadataRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SequenceMetadataRepository sequenceMetadataRepository;

    @Test
    public void save() {
        Task task = new Task(UUID.randomUUID());
        List<Command> commands = new ArrayList<>();
        UUID commandId = UUID.randomUUID();
        commands.add(new Command(UUID.randomUUID(), "address_1", "body_1"));
        commands.add(new Command(commandId, "address_2", "body_2"));
        commands.add(new Command(UUID.randomUUID(), "address_3", "body_3"));

        task.loadCommands(new HashSet<>(commands));

        taskRepository.save(task);

        Set<SequenceMetadata> metadataSet = new HashSet<>();

        for (int i = 0; i < commands.size(); i++) {
            UUID nextCommandId = (i == commands.size() - 1) ? null : commands.get(i + 1).getId();
            metadataSet.add(new SequenceMetadata(commands.get(i), nextCommandId));
        }

//        Set<SequenceMetadata> metadata = new HashSet<>();
//        metadata.add(new SequenceMetadata(UUID.randomUUID(), UUID.randomUUID()));
//        metadata.add(new SequenceMetadata(UUID.randomUUID(), UUID.randomUUID()));
//        metadata.add(new SequenceMetadata(UUID.randomUUID(), UUID.randomUUID()));
//
//        when(sequenceMetadataService.loadIndex(commands)).thenReturn(metadata);

        sequenceMetadataRepository.save(metadataSet);
        entityManager.flush();
        entityManager.clear();

        SequenceMetadata metadata = sequenceMetadataRepository.getByCommandId(commandId);

        assertThat(metadata).isNotNull();
        metadata.getCommand().setStatus(CommandStatus.OK);
        metadata.getCommand().getTask().setStatus(TaskStatus.OK);

        entityManager.flush();

//        metadata = sequenceMetadataRepository.findOne(metadata.getId());
//        Consumer<SequenceIndex> consumer = i -> System.out.println(i.getCommandId() + " ---> " + i.getNextCommandId());
//        metadata.getSequenceIndices().forEach(consumer);
//        metadata = sequenceMetadataRepository.getByCommandId(UUID.randomUUID());
//
//        assertThat(metadata).isNotNull();
    }
}
