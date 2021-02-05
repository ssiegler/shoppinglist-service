package io.github.ssiegler.demos.shoppinglistservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static io.github.ssiegler.demos.shoppinglistservice.ShoppinglistController.SHOPPINGLIST_PATH;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppinglistServiceIT {

    private final TestRestTemplate testRestTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    ShoppinglistServiceIT(TestRestTemplate testRestTemplate, ObjectMapper objectMapper) {
        this.testRestTemplate = testRestTemplate;
        this.objectMapper = objectMapper;
    }

    @Test
    void retrieving_shoppinglist_succeeds() {
        var response = testRestTemplate.getForEntity(SHOPPINGLIST_PATH, List.class);

        assertThat(response)
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void adding_item_succeeds() {
        var response = testRestTemplate.postForEntity(SHOPPINGLIST_PATH, new Item(), null);

        assertThat(response)
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void added_item_is_returned_in_list() {
        Item addedItem = givenItem("new item");

        testRestTemplate.postForEntity(SHOPPINGLIST_PATH, addedItem, null);

        List<?> responseList = testRestTemplate.getForObject(SHOPPINGLIST_PATH, List.class);

        assertThat(responseList)
                .extracting(this::readItem)
                .contains(addedItem);

    }

    @Test
    void added_items_get_unique_id() {
        Item item = givenItem("some item");

        testRestTemplate.postForEntity(SHOPPINGLIST_PATH, item, null);
        testRestTemplate.postForEntity(SHOPPINGLIST_PATH, item, null);

        List<?> responseList = testRestTemplate.getForObject(SHOPPINGLIST_PATH, List.class);

        assertThat(responseList)
                .extracting(this::readItem)
                .extracting(Item::getId)
                .doesNotHaveDuplicates();
    }

    @Test
    void changed_item_keeps_its_id() {
        Item oldItem = givenItem("the old item");

        testRestTemplate.postForEntity(SHOPPINGLIST_PATH, oldItem, null);

        List<?> responseList = testRestTemplate.getForObject(SHOPPINGLIST_PATH, List.class);
        String itemId = findItemIdByDescription(responseList, oldItem.getDescription());

        testRestTemplate.put(SHOPPINGLIST_PATH + "/" + itemId, givenItem("the changed item"));

        List<?> updatedItems = testRestTemplate.getForObject(SHOPPINGLIST_PATH, List.class);
        assertThat(updatedItems)
                .extracting(this::readItem)
                .noneMatch(item -> item.getDescription().equals("the old item"))
                .contains(itemWithId("the changed item", itemId));
    }

    @Test
    void deleted_items_are_removed_from_the_list() {
        Item removedItem = givenItem("delete me");

        testRestTemplate.postForEntity(SHOPPINGLIST_PATH, removedItem, null);

        List<?> responseList = testRestTemplate.getForObject(SHOPPINGLIST_PATH, List.class);
        String itemId = findItemIdByDescription(responseList, removedItem.getDescription());

        testRestTemplate.delete(SHOPPINGLIST_PATH + "/" + itemId);

        List<?> updatedItems = testRestTemplate.getForObject(SHOPPINGLIST_PATH, List.class);
        assertThat(updatedItems)
                .extracting(this::readItem)
                .noneMatch(item -> item.getId().equals(itemId))
                .extracting(Item::getDescription)
                .doesNotContain("delete me");
    }

    private Item itemWithId(String description, String id) {
        Item item = new Item();
        item.setDescription(description);
        item.setId(id);
        return item;
    }

    private String findItemIdByDescription(List<?> responseList, String description) {
        return responseList.stream()
                .map(this::readItem)
                .filter(itemWithId -> description.equals(itemWithId.getDescription()))
                .map(Item::getId)
                .findFirst()
                .orElseThrow();
    }

    private Item givenItem(String description) {
        Item addedItem = new Item();
        addedItem.setDescription(description);
        return addedItem;
    }

    private Item readItem(Object item) {
        return objectMapper.convertValue(item, Item.class);
    }
}
