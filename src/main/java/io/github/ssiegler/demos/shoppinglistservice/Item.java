package io.github.ssiegler.demos.shoppinglistservice;

import java.util.Objects;

/**
 * A shopping list item
 */
public class Item {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                '}';
    }
}
