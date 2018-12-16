package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.converter.TicketConverter;
import construction_and_testing.public_transport_system.domain.DTO.TicketReportDTO;
import construction_and_testing.public_transport_system.domain.Reservation;
import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.service.definition.ReservationService;
import construction_and_testing.public_transport_system.service.definition.TicketService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;



    @Autowired
    private ReservationService reservationService;

    /**
     * POST /api/ticket
     * <p>
     * Controller method for creating a new ticket.
     *
     * @return ticket that is created
     */
    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        logger.info("Adding ticket at time {}.", Calendar.getInstance().getTime());
        Ticket t = this.ticketService.saveTicket(ticket);
        if (t != null) {
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } else {
            throw new GeneralException("Ticket with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /rest/ticket
     * <p>
     * Controller method for finding all tickets.
     *
     * @return all tickets
     */

    //@PreAuhtorized("isAuthenticated()")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Ticket>> findAll() {
        List<Ticket> tickets = this.ticketService.findAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    /**
     * GET /api/ticket/{id}
     * <p>
     * Controller method for finding ticket with a given id.
     *
     * @return ticket with the given id
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ticket> findById(@PathVariable("id") String id) {
        logger.info("Requesting ticket with id {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            Ticket ticket = this.ticketService.findTicketById(Long.parseLong(id));
            return new ResponseEntity<>(ticket, HttpStatus.FOUND);
        } catch (NumberFormatException e) {
            throw new GeneralException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new GeneralException("Requested ticket does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getTicketsForUser/{id}")
    public ResponseEntity<List<TicketReportDTO>> getTicketsForUser(@PathVariable("id") String id)
    {
        List<TicketReportDTO> ticketList = new ArrayList<TicketReportDTO>();

        List<Reservation> reservationList;

        try {
           reservationList  =  reservationService.getReservationsForUser(Long.parseLong(id) );

        } catch (NumberFormatException e) {
            throw new GeneralException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        }

        for (Reservation r: reservationList) {
            for (Ticket t: r.getTickets()) {
                ticketList.add(TicketConverter.fromEntity(t));
            }

        }
        return new ResponseEntity<>(ticketList, HttpStatus.OK);
    }

    @PutMapping("/updateTicket")
    ResponseEntity<Boolean> updateValidator(@RequestBody TicketReportDTO ticketDTO){

        try {
            Optional<Ticket> optionalTicket = Optional.of(this.ticketService.findTicketById(ticketDTO.getId()) );
            ModelMapper mapper = new ModelMapper();
            mapper.map(ticketDTO, optionalTicket.get());
            this.ticketService.saveTicket(optionalTicket.get());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (GeneralException e)
        {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/reprot/{stringDate1}/{stringDate2}")
    ResponseEntity<Map<VehicleType, Integer>> getReport(@PathVariable String stringDate1, @PathVariable String stringDate2)
    {
        try{
            LocalDate date1 = LocalDate.parse(stringDate1);
            LocalDate date2 = LocalDate.parse(stringDate2);

            Map<VehicleType, Integer> prices = this.ticketService.getReport(date1, date2);

            return new ResponseEntity<>(prices, HttpStatus.OK);
        }
        catch (DateTimeParseException de)
        {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_ACCEPTABLE);
        }


    }

}
