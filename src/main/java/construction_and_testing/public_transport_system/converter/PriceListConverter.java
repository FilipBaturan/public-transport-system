package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.ItemDTO;
import construction_and_testing.public_transport_system.domain.DTO.PricelistDTO;
import construction_and_testing.public_transport_system.domain.DTO.PricelistItemDTO;
import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.domain.PricelistItem;

import java.util.HashSet;
import java.util.Set;

public class PriceListConverter extends AbstractConverter {

    public static PricelistDTO fromEntity(Pricelist entity) {
        PricelistDTO dto = new PricelistDTO();
        dto.setId(entity.getId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        Set<PricelistItemDTO> items = new HashSet<>();
        for (PricelistItem pi : entity.getItems()) {
            PricelistItemDTO piDTO = new PricelistItemDTO();
            piDTO.setId(pi.getId());
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(pi.getItem().getId());
            itemDTO.setAge(pi.getItem().getAge());
            itemDTO.setCost(pi.getItem().getCost());
            itemDTO.setType(pi.getItem().getType());
            itemDTO.setVehicleType(pi.getItem().getVehicleType());
            itemDTO.setZone(pi.getItem().getZone().getId());
            piDTO.setItem(itemDTO);
            items.add(piDTO);
        }
        dto.setItems(items);
        return dto;
    }


}
