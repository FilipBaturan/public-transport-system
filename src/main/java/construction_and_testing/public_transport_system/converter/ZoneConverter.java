package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.ZoneDTO;
import construction_and_testing.public_transport_system.domain.DTO.ZoneTransportLineDTO;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.Zone;

import java.util.HashSet;

public class ZoneConverter extends AbstractConverter {

    public static Zone toEntity(ZoneDTO dto) {
        Zone entity = new Zone();
        entity.setId(dto.getId());
        entity.setActive(dto.isActive());
        entity.setName(dto.getName());
        entity.setLines(new HashSet<>());
        for (ZoneTransportLineDTO line : dto.getLines()) {
            TransportLine tranLine = new TransportLine();
            tranLine.setId(line.getId());
            tranLine.setActive(line.isActive());
            tranLine.setType(line.getType());
            tranLine.setName(line.getName());
            HashSet<TransportLine> newLines = (HashSet<TransportLine>) entity.getLines();
            newLines.add(tranLine);
            entity.setLines(newLines);
        }
        return entity;
    }

    public static ZoneDTO fromEntity(Zone entity) {
        return new ZoneDTO(entity);
    }


}
