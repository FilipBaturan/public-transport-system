package construction_and_testing.public_transport_system.domain.DTO;

import java.util.List;

public class StationCollectionDTO {

    private List<StationDTO> stations;

    public StationCollectionDTO() {
    }

    public StationCollectionDTO(List<StationDTO> stations) {
        this.stations = stations;
    }

    public List<StationDTO> getStations() {
        return stations;
    }

    public void setStations(List<StationDTO> stations) {
        this.stations = stations;
    }
}
