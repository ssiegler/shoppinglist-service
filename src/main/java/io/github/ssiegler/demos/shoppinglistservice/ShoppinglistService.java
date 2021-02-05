package io.github.ssiegler.demos.shoppinglistservice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implements the business logic of the shopping list service.
 */
public class ShoppinglistService {
    private final List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void insertItem(Item item) {
        item.setId(UUID.randomUUID().toString());
        items.add(item);
    }

    public void changeItem(String id, Item update) {
        items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .ifPresent(item -> item.setDescription(update.getDescription()));
    }

    public void removeItemById(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }
}