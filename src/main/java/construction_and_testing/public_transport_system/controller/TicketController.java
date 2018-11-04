package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @RequestMapping(method = RequestMethod.GET, value = "/saveTicket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> findTicketById(@RequestBody Ticket ticket) {
        Ticket t = this.ticketService.saveTicket(ticket);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllTickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> findAllTickets() {
        List<Ticket> tickets = this.ticketService.findAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findTicketById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> findTicketById(@PathVariable("id") String id) {
        Ticket ticket = this.ticketService.findTicketById(Long.parseLong(id));
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }
}
