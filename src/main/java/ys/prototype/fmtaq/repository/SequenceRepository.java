package ys.prototype.fmtaq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.domain.Sequence;

import java.util.UUID;

@Repository
public interface SequenceRepository extends CrudRepository<Sequence, UUID> {
}
