package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Pricelist;

import java.util.List;

public interface PricelistService {

    Pricelist savePricelist(Pricelist p);

    void remove(Long id);

    List<Pricelist> findAllPricelists();

    Pricelist findPricelistById(long id);

}
