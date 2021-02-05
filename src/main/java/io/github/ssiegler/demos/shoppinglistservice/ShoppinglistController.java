package io.github.ssiegler.demos.shoppinglistservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shoppinglist")
public class ShoppinglistController {
    List<Item> items = new ArrayList<>();

    @GetMapping(produces = "application/json")
    public List<Item> findAllItems() {
        return items;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody Item item) {
        items.add(item);
    }
}
