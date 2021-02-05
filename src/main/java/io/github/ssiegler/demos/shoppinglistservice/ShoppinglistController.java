package io.github.ssiegler.demos.shoppinglistservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppinglist")
public class ShoppinglistController {
    @GetMapping(produces = "application/json")
    public List findAllItems() {
        return List.of();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody Item item) {

    }
}
