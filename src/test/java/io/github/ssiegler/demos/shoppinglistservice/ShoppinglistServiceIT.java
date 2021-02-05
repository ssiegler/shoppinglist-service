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

    private Item givenItem(String description) {
        Item addedItem = new Item();
        addedItem.setDescription(description);
        return addedItem;
    }

    private Item readItem(Object item) {
        return objectMapper.convertValue(item, Item.class);
    }
}
