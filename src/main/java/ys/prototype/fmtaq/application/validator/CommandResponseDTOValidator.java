package ys.prototype.fmtaq.application.validator;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommandResponseDTOValidator {

    private final Validator validator;

    public CommandResponseDTOValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(CommandResponseDTO commandResponseDTO) {
        Set<ConstraintViolation<CommandResponseDTO>> violations = validator.validate(commandResponseDTO);

        if (violations.size() > 0) {
            Set<String> errorMessages = violations.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new RuntimeException("validate violations for CommandResponseDTO : " + errorMessages);
        }
    }
}
