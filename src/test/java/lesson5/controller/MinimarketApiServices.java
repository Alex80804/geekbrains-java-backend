package lesson5.controller;

import lesson5.model.Category;
import lesson5.model.Product;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class MinimarketApiServices {

    public static String URL = "http://localhost:8189/market/";

    public static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL)
            .build();

    public static Response<List<Product>> showAllProducts() throws IOException {
        return retrofit.create(MinimarketApi.class).getAllProducts().execute();
    }

    public static Response<Product> showProductById(Long id) throws IOException {
        return retrofit.create(MinimarketApi.class).getProductById(id).execute();
    }

    public static Response<Product> createProduct(Product product) throws IOException {
        return retrofit.create(MinimarketApi.class).createProduct(product).execute();
    }

    public static Response<Product> updateProduct(Product product) throws IOException {
        return retrofit.create(MinimarketApi.class).updateProduct(product).execute();
    }

    public static void deleteProductById(Long id) throws IOException {
        retrofit.create(MinimarketApi.class).deleteProductById(id).execute();
    }

    public static Response<Category> getCategoryById(Long id) throws IOException {
        return retrofit.create(MinimarketApi.class).getCategoryById(id).execute();
    }

    public static void main(String[] args) throws IOException {
        //showAllProducts();
        //showProductById(1L);
        //createProduct();
        //updateProduct();
        //deleteProductById(1L);
        //showProductById(1L);
        //System.out.println(getCategoryById(1L).body());
    }
}
