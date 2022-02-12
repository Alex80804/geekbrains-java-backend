package lesson5.tests;

import lesson5.model.Product;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

import static lesson5.controller.MinimarketApiServices.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductNegativeTests {

    @Test
    @Order(1)
    void createProductNegativeTest() throws IOException {
        Product productToCreate = new Product();
        productToCreate.setId(100L);
        productToCreate.setTitle("TestCreate");
        productToCreate.setPrice(1000);
        productToCreate.setCategoryTitle("Food");

        Response<Product> response = createProduct(productToCreate);
        Assertions.assertEquals(400, response.code());
        }

    @Test
    @Order(2)
    void showProductByIdNegativeTest() throws IOException {
        Response<Product> response = showProductById(100L);
        Assertions.assertEquals(404, response.code());
    }

    @Test
    @Order(3)
    void updateProductByIdNegativeTest() throws IOException {
        Product productToUpdate = new Product();
        productToUpdate.setId(100L);
        productToUpdate.setTitle("TestUpdate");
        productToUpdate.setPrice(1200);
        productToUpdate.setCategoryTitle("Food");

        Response<Product> response = updateProduct(productToUpdate);
        Assertions.assertEquals(400, response.code());
    }
}

