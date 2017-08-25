package ys.prototype.fmtaq.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.domain.SequenceMetadata;

import java.util.UUID;

@Repository
public interface SequenceMetadataRepository extends CrudRepository<SequenceMetadata, UUID> {
    @Query("select sm from SequenceMetadata sm left join fetch sm.sequenceIndices si left join sm.task t left join t.commands c where c.id = :id")
    SequenceMetadata getByCommandId(@Param("id") UUID id);
}
