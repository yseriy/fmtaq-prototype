package ys.prototype.fmtaq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.domain.Command;

import java.util.UUID;

@Repository
public interface CommandRepository extends CrudRepository<Command, UUID> {

}
