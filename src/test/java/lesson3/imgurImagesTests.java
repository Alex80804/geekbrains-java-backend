package lesson3;

import common.imgurVariables;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class imgurImagesTests {

    public static List<String> getAccountImagesIds() {
        return
                given()
                        .baseUri(imgurVariables.URL)
                        .auth()
                        .oauth2(imgurVariables.TOKEN)
                        .log().all().
                        when()
                        .basePath(imgurVariables.ACCOUNT_IMAGE_PATH)
                        .log().all()
                        .get().
                        then()
                        .log().all()
                        .contentType(ContentType.JSON).extract().response().jsonPath().getList("data.id");
    }

    public static List<String> getAccountImagesDeleteHashes() {
        return
                given()
                        .baseUri(imgurVariables.URL)
                        .auth()
                        .oauth2(imgurVariables.TOKEN)
                        .log().all().
                        when()
                        .basePath(imgurVariables.ACCOUNT_IMAGE_PATH)
                        .log().all()
                        .get().
                        then()
                        .log().all()
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
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.UPLOAD)
                .log().all()
                .multiPart("image", new File(filePath))
//                .formParam("description", "pic 1")
                .post().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Order(1)
    @Test
    @Disabled
    public void imgurGetAccountImagesTest() {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.ACCOUNT_IMAGE_PATH)
                .log().all()
                .get().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true))
                .contentType(ContentType.JSON).extract().response();
    }

    @Order(2)
    @ParameterizedTest
    @Disabled
    @MethodSource("getAccountImagesIds")
    public void imgurGetImageTest(String imageId) {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.IMAGE_PATH + "/" + imageId)
                .log().all()
                .get().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Order(3)
    @ParameterizedTest
    @Disabled
    @MethodSource("getAccountImagesIds")
    public void imgurUpdateImageAuthedTest(String imageId) {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.IMAGE_PATH + "/" + imageId)
                .log().all()
                .formParam("title", "title updated from authed test")
                .formParam("description", "description updated from authed test")
                .post().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Order(4)
    @ParameterizedTest
    @Disabled
    @MethodSource("getAccountImagesDeleteHashes")
    public void imgurUpdateImageUnAuthedTest(String imageDeleteHash) {
        given()
                .baseUri(imgurVariables.URL)
                .log().all().
                when()
                .basePath(imgurVariables.IMAGE_PATH + "/" + imageDeleteHash)
                .headers("Authorization", "Client-ID " + imgurVariables.CLIENT_ID)
                .formParam("title", "title updated from unauthed test")
                .formParam("description", "description updated from unauthed test")
                .log().all()
                .post().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Order(5)
    @ParameterizedTest
    @Disabled
    @MethodSource("getAccountImagesIds")
    public void imgurSetFavoriteImageTest(String imageId) {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.IMAGE_PATH + "/" + imageId + imgurVariables.FAVORITE_PATH)
                .log().all()
                .post().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Order(6)
    @Test
    @Disabled
    public void imgurGetAccountFavorites() {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.ACCOUNT + "/" + imgurVariables.USERNAME + imgurVariables.FAVORITES_PATH)
                .log().all()
                .get().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Order(7)
    @Test
    @Disabled
    public void imgurCreateAlbum() {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.ALBUM_PATH)
                .log().all()
                .formParam("title", "New test album title")
                .formParam("description", "New test album description")
                .post().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Order(8)
    @ParameterizedTest
    @Disabled
    @MethodSource("getAccountImagesIds")
    public void imgurAddImageToAlbumAuthedTest(String imageId) {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.ALBUM_PATH + "/" + imgurVariables.ALBUM_ID + imgurVariables.ADD)
                .formParam("ids[]", imageId)
                .log().all()
                .put().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }


    @Order(9)
    @ParameterizedTest
    @Disabled
    @MethodSource("getAccountImagesIds")
    public void imgurDeleteImageAuthedTest(String imageId) {
        given()
                .baseUri(imgurVariables.URL)
                .auth()
                .oauth2(imgurVariables.TOKEN)
                .log().all().
                when()
                .basePath(imgurVariables.IMAGE_PATH + "/" + imageId)
                .log().all()
                .delete().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }
/*

    @ParameterizedTest
    @MethodSource("getAccountImagesDeleteHashes")
    public void imgurDeleteImageUnAuthedTest(String imageDeleteHash) {
        given()
                .baseUri(imgurVariables.URL)
                .log().all().
                when()
                .basePath(imgurVariables.IMAGE_PATH + "/" + imageDeleteHash)
                .headers("Authorization", "Client-ID " + imgurVariables.CLIENT_ID)
                .log().all()
                .delete().
                then()
                .log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }
 */

}
