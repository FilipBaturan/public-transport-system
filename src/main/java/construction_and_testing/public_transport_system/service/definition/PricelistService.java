package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Pricelist;

import java.util.List;

public interface PricelistService {

    /**
     * Saving created pricelist with all their items.
     *
     * @param p new pricelist
     * @return saved pricelist
     */
    Pricelist savePricelist(Pricelist p);

    /**
     * Removing pricelist
     *
     * @param id - pricelist id
     */
    void remove(Long id);

    /**
     * Getting all pricelist
     *
     * @return list of all found pricelist
     */
    List<Pricelist> findAllPricelists();

    /**
     * Getting pricelist by given id
     *
     * @param id of pricelist
     * @return - pricelist with given id
     */
    Pricelist findPricelistById(Long id);

    /**
     * Getting active pricelist
     *
     * @return active pricelist
     */
    Pricelist findValid();

    /**
     * Modifying active pricelist
     *
     * @param p - pricelist with modified items
     * @return saved pricelist
     */
    Pricelist modify(Pricelist p);
}
