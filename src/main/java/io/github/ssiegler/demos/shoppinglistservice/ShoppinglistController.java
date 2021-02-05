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
    public void addItem(@RequestBody Item item) {
        shoppinglistService.insertItem(item);
    }

}
