package ys.prototype.fmtaq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.domain.LinkedCommand;

import java.util.UUID;

@Repository
public interface LinkedCommandRepository extends CrudRepository<LinkedCommand, UUID> {
}
