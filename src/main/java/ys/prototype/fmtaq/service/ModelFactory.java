package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import ys.prototype.fmtaq.domain.Group;
import ys.prototype.fmtaq.domain.GroupedCommand;
import ys.prototype.fmtaq.domain.LinkedCommand;
import ys.prototype.fmtaq.domain.Sequence;

import java.util.UUID;

@Service
public class ModelFactory {

    public Sequence createSequence(UUID id) {
        return new Sequence(id);
    }

    public Group createGroup(UUID id) {
        return new Group(id);
    }

    public LinkedCommand createLinkedCommand(UUID id, UUID nextCommandId, String address, String body) {
        return new LinkedCommand(id, nextCommandId, address, body);
    }

    public GroupedCommand createGroupedCommand(UUID id, String address, String body) {
        return new GroupedCommand(id, address, body);
    }
}
