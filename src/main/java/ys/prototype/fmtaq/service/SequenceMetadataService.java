package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.SequenceMetadata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class SequenceMetadataService {

    public Set<SequenceMetadata> loadIndex(List<Command> commands) {
        Set<SequenceMetadata> metadataSet = new HashSet<>();

        for (int i = 0; i < commands.size(); i++) {
            UUID nextCommandId = (i == commands.size() - 1) ? null : commands.get(i + 1).getId();
            metadataSet.add(new SequenceMetadata(commands.get(i), nextCommandId));
        }

        return metadataSet;
    }
}
