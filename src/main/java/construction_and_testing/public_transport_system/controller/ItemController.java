package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.Item;
import construction_and_testing.public_transport_system.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, value = "/saveItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> findItemById(@RequestBody Item item) {
        Item i = this.itemService.saveItem(item);
        return new ResponseEntity<>(i, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> findAllItems() {
        List<Item> items = this.itemService.findAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findItemById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> findItemById(@PathVariable("id") String id) {
        Item item = this.itemService.findItemById(Long.parseLong(id));
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
