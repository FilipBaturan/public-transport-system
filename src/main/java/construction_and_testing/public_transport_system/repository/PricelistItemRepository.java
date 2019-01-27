package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.PricelistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PricelistItemRepository extends JpaRepository<PricelistItem, Long> {


    @Modifying
    @Query(value = "insert into pricelist_item (id, active, item_id, pricelist_id) values (:id, :active, :item, :pricelist)",
            nativeQuery = true)
    void insertPricelistItem(@Param("id") Long id, @Param("active") boolean active, @Param("item") Long item,
                             @Param("pricelist") Long pricelist);


}
