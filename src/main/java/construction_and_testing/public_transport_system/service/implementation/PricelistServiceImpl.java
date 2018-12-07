package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.repository.PricelistRepository;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PricelistServiceImpl implements PricelistService {

    @Autowired
    private PricelistRepository pricelistRepository;

    @Override
    public Pricelist savePricelist(Pricelist p) {
        try {
            return this.pricelistRepository.save(p);
        } catch (DataIntegrityViolationException e) {
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
    public Pricelist findPricelistById(long id) {
        return this.pricelistRepository.findById(id).get();
    }
}
