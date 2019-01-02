package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.converter.ReservationConverter;
import construction_and_testing.public_transport_system.domain.DTO.ReservationDTO;
import construction_and_testing.public_transport_system.service.definition.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public void getAll() {
        //add integration later
    }

    /**
     * Saving new reservation
     *
     * @param reservation new reservation
     * @return true if reservation is saved successfully and OK, false if not and BAD_REQUEST
     */
    @PostMapping("reserve")
    public ResponseEntity<Object> reserve(@RequestBody ReservationDTO reservation){
        boolean success = reservationService.reserve(ReservationConverter.toEntity(reservation), ReservationConverter.getTypes(reservation));
        if(success) {
            return new ResponseEntity<>(success, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
        }
    }

}
