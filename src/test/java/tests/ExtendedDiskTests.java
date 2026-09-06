package tests;

import org.junit.After;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ExtendedDiskTests {

    private static final String TOKEN = "y0__wgBEN_L0M4BGNuWAyCnvp72GIHg69Rr35LHxJbs_WtUqsWO3I3I";
    private String folderName;
    private void createFolder() {
        given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .put("https://cloud-api.yandex.net/v1/disk/resources?path="+folderName)
                .then()
                .statusCode(201)
                .body("href", notNullValue());}
    private void uploadFile() {
        String href = given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .get("https://cloud-api.yandex.net/v1/disk/resources/upload?path="+folderName)
                .then()
                .statusCode(200)
                .extract()
                .path("href");
        given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .put(href)
                .then()
                .statusCode(201);
    }
    @Test
    public void copyFileTest() {
        createFolder();
        uploadFile();
        given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .post("https://cloud-api.yandex.net/v1/disk/resources/copy?from=" + folderName + "/test.txt&path=" + folderName + "_copy/test.txt")
                .then()
                .statusCode(201)
                .body("href", notNullValue());
    }

    @After
    public void deleteFolder() {
        given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .delete("https://cloud-api.yandex.net/v1/disk/resources?path=" + folderName)
                .then()
                .statusCode(204);
    }
}
