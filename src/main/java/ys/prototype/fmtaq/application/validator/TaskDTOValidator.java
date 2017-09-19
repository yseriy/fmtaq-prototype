package ys.prototype.fmtaq.application.validator;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.TaskDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskDTOValidator {

    private final Validator validator;

    public TaskDTOValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(TaskDTO taskDTO) {
        Set<ConstraintViolation<TaskDTO>> violations = validator.validate(taskDTO);

        if (violations.size() > 0) {
            Set<String> errorMessages = violations.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new RuntimeException("validate violations for TaskDTO: " + errorMessages);
        }
    }
}
