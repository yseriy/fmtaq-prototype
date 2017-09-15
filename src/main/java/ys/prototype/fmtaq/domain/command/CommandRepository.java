package ys.prototype.fmtaq.domain.command;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommandRepository extends CrudRepository<Command, UUID> {
}
