package ys.prototype.fmtaq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.SequenceIndex;
import ys.prototype.fmtaq.domain.SequenceMetadata;
import ys.prototype.fmtaq.domain.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceMetadataRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SequenceMetadataRepository sequenceMetadataRepository;

    @Test
    public void save() {
        Task task = new Task(UUID.randomUUID());
        List<Command> commands = new ArrayList<>();
        commands.add(new Command(UUID.randomUUID(), "address_1", "body_1"));
        commands.add(new Command(UUID.randomUUID(), "address_2", "body_2"));
        commands.add(new Command(UUID.randomUUID(), "address_3", "body_3"));

        SequenceMetadata metadata = new SequenceMetadata();
        metadata.load(task, commands);

        sequenceMetadataRepository.save(metadata);
        entityManager.flush();
        entityManager.clear();

//        metadata = sequenceMetadataRepository.findOne(metadata.getId());
//        Consumer<SequenceIndex> consumer = i -> System.out.println(i.getCommandId() + " ---> " + i.getNextCommandId());
//        metadata.getSequenceIndices().forEach(consumer);
//        metadata = sequenceMetadataRepository.getByCommandId(UUID.randomUUID());
//
//        assertThat(metadata).isNotNull();
    }
}
