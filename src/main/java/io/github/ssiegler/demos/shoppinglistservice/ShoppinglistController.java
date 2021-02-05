package io.github.ssiegler.demos.shoppinglistservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides the REST API to the shopping list service
 *
 * @see <a href="https://spring.io/guides/gs/rest-service/">Building a RESTful Web Service</a>
 */
@RestController
@RequestMapping(ShoppinglistController.SHOPPINGLIST_PATH)
public class ShoppinglistController {
    public static final String SHOPPINGLIST_PATH = "/shoppinglist";

    private final ShoppinglistService shoppinglistService;

    @Autowired
    public ShoppinglistController(ShoppinglistService shoppinglistService) {
        this.shoppinglistService = shoppinglistService;
    }

    @GetMapping(produces = "application/json")
    public List<Item> findAllItems() {
        return shoppinglistService.getItems();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    // Returning the URL for a Location header would make tests easier
    public void addItem(@RequestBody Item item) {
        shoppinglistService.insertItem(item);
    }

    @PutMapping(consumes = "application/json", value = "{id}")
    public void updateItem(@PathVariable("id") String id, @RequestBody Item item) {
        shoppinglistService.changeItem(id, item);
    }

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable("id") String id) {
        shoppinglistService.removeItemById(id);
    }
}
