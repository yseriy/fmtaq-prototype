package ys.prototype.fmtaq.application.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.domain.TaskType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TaskDTOValidator.class, ValidatorConfig.class})
public class TaskDTOValidatorTest {

    @Autowired
    private TaskDTOValidator taskDTOValidator;

    @Test
    public void validate() throws Exception {
        Stream<Integer> integerStream = Stream.iterate(0, n -> n + 1);
        Stream<CommandDTO> commandDTOStream = integerStream.map(this::createCommandDTO);
        List<CommandDTO> commandDTOList = commandDTOStream.limit(3).collect(Collectors.toList());
        TaskDTO taskDTO = new TaskDTO(TaskType.SEQUENCE, commandDTOList);

        taskDTOValidator.validate(taskDTO);
    }

    private CommandDTO createCommandDTO(Integer i) {
        return new CommandDTO("address_" + i, "body_" + i);
    }
}
