package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Item;
import construction_and_testing.public_transport_system.domain.enums.AgeType;
import construction_and_testing.public_transport_system.domain.enums.TicketType;
import construction_and_testing.public_transport_system.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item saveItem(Item i) { return this.itemRepository.save(i); }

    @Override
    public List<Item> findAllItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public Item findItemById(long id) {
        return this.itemRepository.findById(id).get();
    }

}
