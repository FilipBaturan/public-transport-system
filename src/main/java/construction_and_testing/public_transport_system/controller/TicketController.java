package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    /**
     * POST /api/ticket
     *
     * Controller method for creating a new ticket.
     *
     * @return ticket that is created
     */
    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        logger.info("Adding ticket at time {}.", Calendar.getInstance().getTime());
        Ticket t = this.ticketService.saveTicket(ticket);
        if( t != null){
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        }else{
            throw new GeneralException("Ticket with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /rest/ticket
     *
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
     *
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
}
