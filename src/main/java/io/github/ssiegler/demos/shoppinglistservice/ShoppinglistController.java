package io.github.ssiegler.demos.shoppinglistservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shoppinglist")
public class ShoppinglistController {
    @GetMapping(produces = "application/json")
    public List findAllItems() {
        return List.of();
    }
}
