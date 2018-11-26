package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.Item;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    /**
     * POST /api/item/{id}
     *
     * Controller method for adding new items
     *
     * @param item - item that we want to add
     * @return added item
     */
    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        logger.info("Adding item at time {}.", Calendar.getInstance().getTime());
        Item i = this.itemService.saveItem(item);
        if( i != null){
            return new ResponseEntity<>(i, HttpStatus.CREATED);
        }else{
            throw new GeneralException("Item with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /api/item
     *
     * Finds all items
     *
     * @return all items
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Item>> findAll() {
        logger.info("Requesting all available items at time {}.", Calendar.getInstance().getTime());
        List<Item> items = this.itemService.findAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * GET /api/item/{id}
     *
     * Finds an item by given id
     *
     * @return item with given id
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Item> findById(@PathVariable("id") String id) {
        logger.info("Requesting item with id {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            Item item = this.itemService.findItemById(Long.parseLong(id));
            return new ResponseEntity<>(item, HttpStatus.FOUND);
        } catch (NumberFormatException e) {
            throw new GeneralException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new GeneralException("Requested item does not exist!", HttpStatus.NOT_FOUND);
        }
    }

}
