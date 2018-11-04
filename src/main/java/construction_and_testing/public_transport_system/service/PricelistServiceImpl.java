package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.repository.PricelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PricelistServiceImpl implements PricelistService {

    @Autowired
    private PricelistRepository pricelistRepository;

    @Override
    public Pricelist savePricelist(Pricelist p) {
        return this.pricelistRepository.save(p);
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
