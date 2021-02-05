package io.github.ssiegler.demos.shoppinglistservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppinglistServiceIT {

    private final TestRestTemplate testRestTemplate;

    @Autowired
    ShoppinglistServiceIT(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Test
    void retrieving_shoppinglist_succeeds() {
        var response = testRestTemplate.getForEntity("/shoppinglist", List.class);

        assertThat(response).extracting(ResponseEntity::getStatusCode).isEqualTo(HttpStatus.OK);
    }

    @Test
    void adding_item_succeeds() {
        var response = testRestTemplate.postForEntity("/shoppinglist", new Item(), null);

        assertThat(response).extracting(ResponseEntity::getStatusCode).isEqualTo(HttpStatus.CREATED);
    }
}
