package ys.prototype.fmtaq.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommandRepository extends CrudRepository<Command, UUID> {

    @Query("select c from Command c join fetch c.task where c.task = :task and c.status = :status order by c.step")
    List<Command> getNextCommand(@Param("task") Task task, @Param("status") CommandStatus status, Pageable pageable);
}
