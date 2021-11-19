package lesson4;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static lesson4.imgurImagesCommon.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class imgurImagesRefactoredTests {

    public static List<String> getAccountImagesIds() {
        return
                given()
                        .spec(accountImageRequestSpec).
                        when()
                        .get().
                        then()
                        .contentType(ContentType.JSON).extract().response().jsonPath().getList("data.id");
    }

    public static List<String> getAccountImagesDeleteHashes() {
        return
                given()
                        .spec(accountImageRequestSpec).
                        when()
                        .get().
                        then()
                        .contentType(ContentType.JSON).extract().response().jsonPath().getList("data.deletehash");
    }

    @Order(0)
    @ParameterizedTest
    @ValueSource(strings = {"F:\\geek\\backend\\lesson3\\shpitz.jpg",
            "F:\\geek\\backend\\lesson3\\dn.jpg",
            "F:\\geek\\backend\\lesson3\\product-4.jpg",
            "F:\\geek\\backend\\lesson3\\product-4-sm.jpg"})
    public void imgurUploadImageTest(String filePath) {

        given()
                .spec(uploadImageRequestSpec).
                when()
                .multiPart("image", new File(filePath))
                .post().
                then()
                .spec(baseResponseSpec);
    }

    @Order(1)
    @Test
    public void imgurGetAccountImagesTest() {
        given()
                .spec(accountImageRequestSpec).
                when()
                .get().
                then()
                .spec(baseResponseSpec)
                .contentType(ContentType.JSON).extract().response();
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("getAccountImagesIds")
    public void imgurGetImageTest(String imageId) {
        given()
                .spec(baseRequestSpec).
                when()
                .basePath(imgurBase.IMAGE_PATH + "/" + imageId)
                .get().
                then()
                .spec(baseResponseSpec);
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("getAccountImagesIds")
    public void imgurUpdateImageAuthedTest(String imageId) {
        given()
                .spec(updateImageAuthedRequestSpec).
                when()
                .basePath(imgurBase.IMAGE_PATH + "/" + imageId)
                .post().
                then()
                .spec(baseResponseSpec);
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("getAccountImagesDeleteHashes")
    public void imgurUpdateImageUnAuthedTest(String imageDeleteHash) {
        given()
                .spec(updateImageUnAuthedRequestSpec).
                when()
                .basePath(imgurBase.IMAGE_PATH + "/" + imageDeleteHash)
                .post().
                then()
                .spec(baseResponseSpec);
    }

    @Order(5)
    @ParameterizedTest
    @MethodSource("getAccountImagesIds")
    public void imgurSetFavoriteImageTest(String imageId) {
        given()
                .spec(baseRequestSpec).
                when()
                .basePath(imgurBase.IMAGE_PATH + "/" + imageId + imgurBase.FAVORITE_PATH)
                .post().
                then()
                .spec(baseResponseSpec);
    }

    @Order(6)
    @Test
    public void imgurGetAccountFavorites() {
        given()
                .spec(accountFavoritesRequestSpec).
                when()
                .log().all()
                .get().
                then()
                .spec(baseResponseSpec);
    }

    @Order(7)
    @Test
    public void imgurCreateAlbum() {
        given()
                .spec(createAlbumRequestSpec).
                when()
                .post().
                then()
                .spec(baseResponseSpec);
    }

    @Order(8)
    @ParameterizedTest
    @MethodSource("getAccountImagesIds")
    public void imgurAddImageToAlbumAuthedTest(String imageId) {
        given()
                .spec(albumRequestSpec).
                when()
                .formParam("ids[]", imageId)
                .put().
                then()
                .spec(baseResponseSpec);
    }


    @Order(9)
    @ParameterizedTest
    @MethodSource("getAccountImagesIds")
    public void imgurDeleteImageAuthedTest(String imageId) {
        given()
                .spec(baseRequestSpec).
                when()
                .basePath(imgurBase.IMAGE_PATH + "/" + imageId)
                .delete().
                then()
                .spec(baseResponseSpec);
    }
}
