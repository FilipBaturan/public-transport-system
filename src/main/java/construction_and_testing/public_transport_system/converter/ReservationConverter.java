package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.*;
import construction_and_testing.public_transport_system.domain.DTO.ReservationDTO;
import construction_and_testing.public_transport_system.domain.DTO.TicketDTO;
import construction_and_testing.public_transport_system.domain.enums.TicketType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservationConverter extends AbstractConverter {

    public static ReservationDTO fromEntity(Reservation entity){
        return new ReservationDTO();
    }

    public static Reservation toEntity(ReservationDTO dto){
        Reservation entity = new Reservation();
        RegisteredUser u = new RegisteredUser();
        u.setId(dto.getOwner());
        System.out.println("Korisnikov id: " + dto.getOwner());
        entity.setOwner(u);
        Set<Ticket> tickets = new HashSet<>();
        for(TicketDTO tDTO : dto.getTickets()){
            Ticket t = new Ticket();
            t.setActive(true);
            t.setPurchaseDate(tDTO.getPurchaseDate().plusHours(1));
            TransportLine line = new TransportLine();
            line.setId(tDTO.getLine());
            t.setLine(line);
            PricelistItem item = new PricelistItem();
            item.setId(tDTO.getPricelistitem());
            t.setPriceList(item);
            t.setToken("dfger");
            tickets.add(t);
            System.out.println(tickets.size());
        }

        entity.setTickets(tickets);
        return entity;
    }

    public static List<TicketType> getTypes(ReservationDTO dto){
        List<TicketType> types = new ArrayList<>();
        for(TicketDTO t : dto.getTickets()){
            types.add(t.getTicketType());
        }
        return types;
    }

}
