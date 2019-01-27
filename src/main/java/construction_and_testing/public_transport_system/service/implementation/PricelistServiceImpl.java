package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Item;
import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.domain.PricelistItem;
import construction_and_testing.public_transport_system.repository.ItemRepository;
import construction_and_testing.public_transport_system.repository.PricelistItemRepository;
import construction_and_testing.public_transport_system.repository.PricelistRepository;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import construction_and_testing.public_transport_system.util.PricelistTimeIntervalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PricelistServiceImpl implements PricelistService {

    @Autowired
    private PricelistRepository pricelistRepository;

    @Autowired
    private PricelistItemRepository pricelistItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Pricelist savePricelist(Pricelist p) {
        try {
            Pricelist pricelist = this.findValid();
            if(pricelist != null){
                List<Pricelist> afterPricelists = this.getAllAfterNow();
                for(Pricelist price : afterPricelists){
                    if(!(price.getEndDate().isBefore(p.getStartDate()) || price.getStartDate().isAfter(p.getEndDate()))){
                        throw new PricelistTimeIntervalException("Cannot create price list with this perion of validity!", HttpStatus.CONFLICT);
                    }
                }
            }
            Pricelist price = pricelistRepository.save(p);
            return price;
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    @Override
    public Pricelist modify(Pricelist p) {
        try{
            LocalDateTime newDate = LocalDateTime.now();
            LocalDateTime oldEndDate = p.getEndDate();
            Set<PricelistItem> newItems = p.getItems();
            p.setItems(new HashSet<>());
            p.setEndDate(newDate);
            pricelistRepository.saveAndFlush(p);
            Pricelist newPricelist = new Pricelist();
            newPricelist.setStartDate(newDate);
            newPricelist.setEndDate(oldEndDate);
            newPricelist.setItems(newItems);
            return pricelistRepository.save(newPricelist);
        }catch(DataIntegrityViolationException e){
            return null;
        }

    }

    @Override
    public void remove(Long id) {
        Optional<Pricelist> entity = pricelistRepository.findById(id);
        if (entity.isPresent()) {
            Pricelist pricelist = entity.get();
            pricelist.setActive(false);
            pricelistRepository.save(pricelist);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<Pricelist> findAllPricelists() {
        return this.pricelistRepository.findAll();
    }

    @Override
    public Pricelist findPricelistById(Long id) {
        if(pricelistRepository.findById(id).isPresent()){
            return pricelistRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Pricelist findValid() {
        List<Pricelist> pricelists = pricelistRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Pricelist p : pricelists) {
            if (p.getStartDate().isBefore(now) && p.getEndDate().isAfter(now)){
                return p;
            }
        }
        return null;
    }



    private List<Pricelist> getAllAfterNow(){
        List<Pricelist> all = pricelistRepository.findAll();
        List<Pricelist> after = new ArrayList<>();
        for(Pricelist p : all){
            if(p.getEndDate().isAfter(LocalDateTime.now())){
                after.add(p);
            }
        }
        return after;
    }

}
