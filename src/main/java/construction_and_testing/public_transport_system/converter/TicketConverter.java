package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.TicketDTO;
import construction_and_testing.public_transport_system.domain.Ticket;

public class TicketConverter extends AbstractConverter {

    public static TicketDTO fromEntity(Ticket t) {
        return new TicketDTO();
    }

    public static Ticket toEntity(TicketDTO dto) {
        return new Ticket();
    }

}
