package lesson5.controller;

import lesson5.model.Category;
import lesson5.model.Product;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MinimarketApi {

    @GET("api/v1/products")
    Call<List<Product>> getAllProducts();

    @GET("api/v1/products/{id}")
    Call<Product> getProductById(@Path("id") Long id);

    @POST("api/v1/products")
    Call<Product> createProduct(@Body Product product);

    @PUT("api/v1/products")
    Call<Product> updateProduct(@Body Product product);

    @DELETE("api/v1/products/{id}")
    Call<Void> deleteProductById(@Path("id") Long id);

    @GET("api/v1/categories/{id}")
    Call<Category> getCategoryById(@Path("id") Long id);
}
