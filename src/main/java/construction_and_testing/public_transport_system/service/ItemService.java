package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Item;
import construction_and_testing.public_transport_system.domain.enums.AgeType;
import construction_and_testing.public_transport_system.domain.enums.TicketType;

import java.util.List;

public interface ItemService {

    Item saveItem(Item i);

    void remove(Long id);

    List<Item> findAllItems();

    Item findItemById(long id);

}
