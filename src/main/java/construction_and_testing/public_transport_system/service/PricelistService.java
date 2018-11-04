package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Pricelist;

import java.util.List;

public interface PricelistService {

    Pricelist savePricelist(Pricelist p);

    List<Pricelist> findAllPricelists();

    Pricelist findPricelistById(long id);

}