package construction_and_testing.public_transport_system.domain.DTO;

import java.time.LocalDateTime;
import java.util.Set;

public class PricelistDTO {

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Set<PricelistItemDTO> items;

    public PricelistDTO() {
    }

    public PricelistDTO(Long id, LocalDateTime startDate, LocalDateTime endDate, Set<PricelistItemDTO> items) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<PricelistItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<PricelistItemDTO> items) {
        this.items = items;
    }
}
