package ys.prototype.fmtaq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Test
    public void save() {

    }
}