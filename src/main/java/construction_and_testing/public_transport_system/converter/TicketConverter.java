package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.TicketReportDTO;
import construction_and_testing.public_transport_system.domain.Ticket;

public class TicketConverter extends AbstractConverter {

    public static TicketReportDTO fromEntity(Ticket t) {
        return new TicketReportDTO(t);
    }

    public static Ticket toEntity(TicketReportDTO dto) {
        return new Ticket();
    }

}
