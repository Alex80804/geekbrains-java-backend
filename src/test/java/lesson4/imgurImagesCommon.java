package lesson4;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.core.Is.is;

public class imgurImagesCommon {

    public static RequestSpecification baseRequestSpec = new RequestSpecBuilder()
            .setBaseUri(imgurBase.URL)
            .addHeader("Authorization", "Bearer " + imgurBase.TOKEN)
            .log(LogDetail.ALL)
            .build();

    public static RequestSpecification baseUnAuthedRequestSpec = new RequestSpecBuilder()
            .setBaseUri(imgurBase.URL)
            .addHeader("Authorization", "Client-ID " + imgurBase.CLIENT_ID)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification baseResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("success", is(true))
            .log(LogDetail.ALL)
            .build();

    public static RequestSpecification uploadImageRequestSpec = new RequestSpecBuilder()
            .addRequestSpecification(baseRequestSpec)
            .setBasePath(imgurBase.UPLOAD)
            .build();

    public static RequestSpecification accountImageRequestSpec = new RequestSpecBuilder()
            .addRequestSpecification(baseRequestSpec)
            .setBasePath(imgurBase.ACCOUNT_IMAGE_PATH)
            .build();

    public static RequestSpecification updateImageAuthedRequestSpec = new RequestSpecBuilder()
            .addRequestSpecification(baseRequestSpec)
            .addFormParam("title", "title updated from test")
            .addFormParam("description", "description updated from test")
            .build();

    public static RequestSpecification updateImageUnAuthedRequestSpec = new RequestSpecBuilder()
            .addRequestSpecification(baseUnAuthedRequestSpec)
            .addFormParam("title", "title updated from test")
            .addFormParam("description", "description updated from test")
            .build();

    public static RequestSpecification accountFavoritesRequestSpec = new RequestSpecBuilder()
            .addRequestSpecification(baseRequestSpec)
            .setBasePath(imgurBase.ACCOUNT + "/" + imgurBase.USERNAME + imgurBase.FAVORITES_PATH)
            .build();

    public static RequestSpecification createAlbumRequestSpec = new RequestSpecBuilder()
            .addRequestSpecification(baseRequestSpec)
            .setBasePath(imgurBase.ALBUM_PATH)
            .addFormParam("title", "New test album title")
            .addFormParam("description", "New test album description")
            .build();

    public static RequestSpecification albumRequestSpec = new RequestSpecBuilder()
            .addRequestSpecification(baseRequestSpec)
            .setBasePath(imgurBase.ALBUM_PATH + "/" + imgurBase.ALBUM_ID + imgurBase.ADD)
            .build();
}
