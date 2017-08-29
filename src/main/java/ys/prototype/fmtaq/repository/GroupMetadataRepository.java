package ys.prototype.fmtaq.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.domain.GroupMetadata;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.UUID;

@Repository
public interface GroupMetadataRepository extends CrudRepository<GroupMetadata, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "1000"))
    @Query("select gm from GroupMetadata gm left join fetch gm.command c left join fetch c.task t where c.id = :id")
    GroupMetadata getByCommandId(@Param("id") UUID id);
}
