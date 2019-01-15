package construction_and_testing.public_transport_system.service.unit;


import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.repository.ReservationRepository;
import construction_and_testing.public_transport_system.repository.TicketRepository;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.implementation.TicketServiceImpl;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.TicketConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketServiceImplUnitTest {

    @MockBean
    TicketRepository ticketRepository;

    @Autowired
    TicketServiceImpl ticketService;

    @MockBean
    ReservationRepository reservationRepository;

    @MockBean
    TransportLineRepository transportLineRepository;


    @Before
    public void setUp() {
        when(ticketRepository.findById(DB_ID)).thenReturn(Optional.of(DB_TICKET));

        when(reservationRepository.findById(DB_RESERVATION_ID)).thenReturn(Optional.of(DB_RESERVATION));

        when(transportLineRepository.findById(DB_LINE_ID)).thenReturn(Optional.of(DB_LINE));

        when(ticketRepository.save(DB_NEW_TICKET)).thenReturn(DB_SAVED_TICKET);
        when(ticketRepository.save(DB_CHANGED_TICKET_TO_SAVE)).thenReturn(DB_CHANGED_TICKET);

        when(ticketRepository.findAll()).thenReturn(DB_TICKET_LIST);

        when(transportLineRepository.findById(DB_TL_INVALID_ID)).thenThrow(new GeneralException());
        when(transportLineRepository.findById(null)).thenReturn(null);

        when(ticketRepository.findById(DB_ID_REMOVE)).thenReturn(Optional.of(DB_REMOVED_TICKET));
        when(ticketRepository.save(DB_REMOVED_TICKET)).thenReturn(DB_REMOVED_TICKET);

        when(ticketRepository.getTiketsForReservation(DB_RESERVATION_ID)).thenReturn(DB_TICKET_LIST);

        when(ticketRepository.getPrice(DB_DATE1, DB_DATE2, 0)).thenReturn(300L);
        when(ticketRepository.getPrice(DB_DATE1, DB_DATE2, 1)).thenReturn(0L);
        when(ticketRepository.getPrice(DB_DATE1, DB_DATE2, 2)).thenReturn(0L);

        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(DB_TICKET);
        ticketList.add(DB_BOUGHT_TICKET2);

        when(ticketRepository.getTicketsBetween(DB_REPORT_START_DATE, DB_REPORT_END_DATE)).thenReturn(ticketList);
    }


    @Transactional
    @Test
    public void saveChangedValidTest() {
        Ticket t1 = ticketService.findTicketById(DB_ID);
        t1.setPurchaseDate(DB_RANDOM_DATE);
        Ticket savedTicket = ticketService.saveTicket(t1);

        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_RANDOM_DATE);
        assertThat(savedTicket.getToken()).isEqualTo("changed");
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);

        List<Ticket> tickets = ticketService.findAllTickets();
        assertThat(tickets.size()).isEqualTo(DB_COUNT);

        verify(ticketRepository, times(1)).save(DB_CHANGED_TICKET_TO_SAVE);
        verify(transportLineRepository, times(1)).findById(DB_LINE_ID);
        verify(reservationRepository, times(1)).findById(DB_RESERVATION_ID);

    }

    @Test
    @Transactional
    public void saveNewValidTest() {

        Ticket savedTicket = ticketService.saveTicket(DB_NEW_TICKET);

        assertThat(savedTicket.getId()).isEqualTo(3L);
        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);

        verify(ticketRepository, times(1)).save(DB_NEW_TICKET);
        verify(transportLineRepository, times(1)).findById(DB_LINE_ID);
        verify(reservationRepository, times(1)).findById(DB_RESERVATION_ID);

    }

    @Transactional
    @Test(expected = GeneralException.class)
    public void saveInvalidLine() {

        Ticket savedTicket = ticketService.saveTicket(DB_INVALID_LINE_TICKET);
        verify(reservationRepository, times(1)).findById(DB_RESERVATION_ID);
        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);

    }

    @Transactional
    @Test(expected = GeneralException.class)
    public void saveNullLine() {

        Ticket savedTicket = ticketService.saveTicket(DB_NULL_LINE_TICKET);
        verify(reservationRepository, times(1)).findById(DB_RESERVATION_ID);
        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);

    }

    @Test(expected = GeneralException.class)
    public void saveInvalidReservation() {
        Ticket savedTicket = ticketService.saveTicket(DB_INVALID_RES_TICKET);
        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getLine()).isEqualTo(DB_LINE);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);

    }

    @Test(expected = GeneralException.class)
    public void saveNullReservation() {

        Ticket savedTicket = ticketService.saveTicket(DB_NULL_RES_TICKET);
        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getLine()).isEqualTo(DB_LINE);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);
    }

    @Transactional
    @Test
    public void removeValidTest() {
        ticketService.remove(DB_ID_REMOVE);
        Ticket removedTicket = ticketService.findTicketById(DB_ID_REMOVE);
        assertFalse(removedTicket.isActive());
        assertThat(removedTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(removedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(removedTicket.getLine()).isEqualTo(DB_LINE);
        assertThat(removedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);
        verify(ticketRepository, times(1)).save(DB_REMOVED_TICKET);
        verify(ticketRepository, times(2)).findById(DB_ID_REMOVE);
    }

    @Transactional
    @Test(expected = EntityNotFoundException.class)
    public void removeNullParameter() {
        ticketService.remove(null);
        verify(ticketRepository, times(1)).save(DB_REMOVED_TICKET);
        verify(ticketRepository, times(1)).findById(DB_ID_REMOVE);
    }

    @Transactional
    @Test(expected = EntityNotFoundException.class)
    public void removeInvalidParameter() {
        ticketService.remove(DB_ID_INVALID);
        verify(ticketRepository, times(1)).save(DB_REMOVED_TICKET);
        verify(ticketRepository, times(1)).findById(DB_ID_REMOVE);
    }

    @Test
    public void getTicketsForResValid() {
        List<Ticket> usersTickets = ticketService.getTiketsForReservation(DB_RESERVATION_ID);
        assertThat(usersTickets).isNotNull();
        assertThat(usersTickets).hasSize(2);
        assertThat(usersTickets.get(0).getId()).isEqualTo(DB_ID);
        assertThat(usersTickets.get(0).getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        verify(ticketRepository, times(1)).getTiketsForReservation(DB_RESERVATION_ID);
    }

    @Test
    public void getTicketsForResInvalid() {
        List<Ticket> usersTickets = ticketService.getTiketsForReservation(DB_RES_INVALID_ID);
        assertThat(usersTickets).isEmpty();
        assertThat(usersTickets).hasSize(0);
        verify(ticketRepository, times(1)).getTiketsForReservation(DB_RES_INVALID_ID);
    }

    @Test()
    public void getTicketsForResNull() {
        List<Ticket> usersTickets = ticketService.getTiketsForReservation(null);
        assertThat(usersTickets).isEmpty();
        assertThat(usersTickets).hasSize(0);
        verify(ticketRepository, times(1)).getTiketsForReservation(null);
    }

    @Test
    public void getReportValid() {
        Map<VehicleType, Integer> prices = ticketService.getReport(DB_DATE1, DB_DATE2);
        assertThat(prices).isNotEmpty();
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(300);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(0);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(0);
    }

    @Test
    public void getReportReverseDates() {
        LocalDate date1 = LocalDate.of(2014, 12, 21);
        LocalDate date2 = LocalDate.of(2018, 3, 8);

        Map<VehicleType, Integer> prices = ticketService.getReport(date2, date1);
        assertThat(prices).isNotEmpty();
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(-1);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(-1);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(-1);
    }

    @Test(expected = NullPointerException.class)
    public void getReportNullDates() {
        Map<VehicleType, Integer> prices = ticketService.getReport(null, null);
        assertThat(prices).isNotEmpty();
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(0);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(0);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(0);
    }

    @Test
    public void getVisitsByWeekValid() {
        Map<String, Integer> prices = ticketService.getVisitsByWeek(DB_REPORT_START_DATE, DB_REPORT_END_DATE);
        verify(ticketRepository, times(1)).getTicketsBetween(DB_REPORT_START_DATE, DB_REPORT_END_DATE);
        assertThat(prices).isNotEmpty();
        assertThat(prices.size()).isEqualTo(1);
        assertThat(prices.get("2016-12-26T00:00,2016-12-19T00:00")).isEqualTo(2);
    }

    @Test
    public void getVisitsByWeekInvalid() {
        Map<String, Integer> prices = ticketService.getVisitsByWeek(DB_REPORT_END_DATE, DB_REPORT_START_DATE);
        assertThat(prices).isEmpty();
        assertThat(prices.size()).isEqualTo(0);
        verify(ticketRepository, times(1)).getTicketsBetween(DB_REPORT_END_DATE, DB_REPORT_START_DATE);
    }

    @Test
    public void getVisitsByMonthValid() {
        Map<String, Integer> prices = ticketService.getVisitsByMonth(DB_REPORT_START_DATE, DB_REPORT_END_DATE);
        verify(ticketRepository, times(1)).getTicketsBetween(DB_REPORT_START_DATE, DB_REPORT_END_DATE);
        assertThat(prices).isNotEmpty();
        assertThat(prices.size()).isEqualTo(1);
        assertThat(prices.get("2016-DECEMBER")).isEqualTo(2);
    }

    @Test
    public void getVisitsByMonthInvalid() {
        Map<String, Integer> prices = ticketService.getVisitsByMonth(DB_REPORT_END_DATE, DB_REPORT_START_DATE);
        verify(ticketRepository, times(1)).getTicketsBetween(DB_REPORT_END_DATE, DB_REPORT_START_DATE);
        assertThat(prices).isEmpty();
        assertThat(prices.size()).isEqualTo(0);
    }


}
