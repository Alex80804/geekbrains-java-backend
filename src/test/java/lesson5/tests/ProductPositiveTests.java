package lesson5.tests;

import lesson5.model.Product;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static lesson5.controller.MinimarketApiServices.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductPositiveTests {

    static Product product = new Product();
    static Integer productCount;

    @Test
    @Order(0)
    void getProductsFirstTest() throws IOException {
        Response<List<Product>> response = showAllProducts();
        Assertions.assertEquals(200, response.code());
        productCount = response.body().size();
    }

    @Test
    @Order(1)
    void createProductTest() throws IOException {
        Product productToCreate = new Product();
        productToCreate.setTitle("TestCreate");
        productToCreate.setPrice(1000);
        productToCreate.setCategoryTitle("Food");

        Response<Product> response = createProduct(productToCreate);
        product = response.body();
        Assertions.assertEquals(201, response.code());
        Assertions.assertEquals("TestCreate", product.getTitle());
    }

    @Test
    @Order(2)
    void showProductByIdTest() throws IOException {
        Response<Product> response = showProductById(product.getId());
        Assertions.assertEquals(200, response.code());
        Assertions.assertEquals(product.getTitle(), response.body().getTitle());
    }

    @Test
    @Order(3)
    void updateProductByIdTest() throws IOException {
        Product productToUpdate = new Product();
        productToUpdate.setId(product.getId());
        productToUpdate.setTitle("TestUpdate");
        productToUpdate.setPrice(1200);
        productToUpdate.setCategoryTitle("Food");

        Response<Product> response = updateProduct(productToUpdate);
        product = response.body();
        Assertions.assertEquals(200, response.code());
        Assertions.assertEquals("TestUpdate", product.getTitle());
    }

    @Test
    @Order(4)
    void getProductsLastTest() throws IOException {
        deleteProductById(product.getId());
        Response<List<Product>> response = showAllProducts();
        Assertions.assertEquals(200, response.code());
        Assertions.assertEquals(productCount, (Integer) response.body().size());
    }
}

