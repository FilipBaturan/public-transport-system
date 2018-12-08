package construction_and_testing.public_transport_system.domain.DTO;

import java.util.List;

public class TransportLineColletionDTO {

    private List<TransportLineDTO> transportLines;

    public TransportLineColletionDTO() {
    }

    public TransportLineColletionDTO(List<TransportLineDTO> transportLines) {
        this.transportLines = transportLines;
    }

    public List<TransportLineDTO> getTransportLines() {
        return transportLines;
    }

    public void setTransportLines(List<TransportLineDTO> transportLines) {
        this.transportLines = transportLines;
    }
}
