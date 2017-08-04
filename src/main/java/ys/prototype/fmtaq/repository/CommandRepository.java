package ys.prototype.fmtaq.repository;

import org.springframework.data.repository.CrudRepository;
import ys.prototype.fmtaq.domain.Command;

import java.util.UUID;

public interface CommandRepository extends CrudRepository<Command, UUID> {
}
