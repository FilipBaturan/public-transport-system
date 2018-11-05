package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Item;
import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.enums.AgeType;
import construction_and_testing.public_transport_system.domain.enums.TicketType;
import construction_and_testing.public_transport_system.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item saveItem(Item i) {
        try {
            return this.itemRepository.save(i);
        } catch (DataIntegrityViolationException e){
            return null;
        }
    }

    @Override
    public void remove(Long id) {
        Optional<Item> entity = itemRepository.findById(id);
        if(entity.isPresent()){
            Item item = entity.get();
            item.setActive(false);
            itemRepository.save(item);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<Item> findAllItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public Item findItemById(long id) {
        return this.itemRepository.findById(id).get();
    }

}
