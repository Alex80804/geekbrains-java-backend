package lesson5.tests;

import lesson5.model.Category;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

import static lesson5.controller.MinimarketApiServices.getCategoryById;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryTests {

    @Test
    @Order(0)
    void getCategoryByIdPositiveTest() throws IOException {
        Response<Category> response = getCategoryById(1L);
        Assertions.assertEquals(200, response.code());
        Assertions.assertEquals("Food", response.body().getTitle());
    }

    @Test
    @Order(1)
    void getCategoryByIdNegativeTest() throws IOException {
        Response<Category> response = getCategoryById(3L);
        Assertions.assertEquals(404, response.code());
    }
}
