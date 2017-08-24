package ys.prototype.fmtaq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.domain.GroupCommand;

import java.util.UUID;

@Repository
public interface GroupCommandRepository extends CrudRepository<GroupCommand, UUID> {
}
