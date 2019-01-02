package construction_and_testing.public_transport_system.domain.DTO;

public class PricelistItemDTO {

    private Long id;

    private ItemDTO item;

    public PricelistItemDTO() {
    }

    public PricelistItemDTO(Long id, ItemDTO item) {
        this.id = id;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }
}
