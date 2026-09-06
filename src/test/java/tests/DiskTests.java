package tests;

import org.junit.After;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class DiskTests {

    private static final String TOKEN = "y0__wgBEN_L0M4BGNuWAyCnvp72GIHg69Rr35LHxJbs_WtUqsWO3I3I";
    private String folderName;
    @Test
    public void getDiskInfo() {
        given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .get("https://cloud-api.yandex.net/v1/disk/")
                .then()
                .statusCode(200)
                .body("total_space", notNullValue());
    }
    @Test
    public void putDiskInfo() {
        folderName = "test_" + System.currentTimeMillis();
        given()
                    .header("Authorization", "OAuth " + TOKEN)
                    .when()
                    .put("https://cloud-api.yandex.net/v1/disk/resources?path="+folderName)
                    .then()
                    .statusCode(201)
                    .body("href", notNullValue());}


    @Test
    public void postDiskInfo()
    {
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
    public void deleteCreatedFolder() {
        given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .delete("https://cloud-api.yandex.net/v1/disk/resources?path="+folderName)
                .then()
                .statusCode(204);
    }
    @After
    public void deleteFolder(){
        given()
                .header("Authorization", "OAuth " + TOKEN)
                .when()
                .delete("https://cloud-api.yandex.net/v1/disk/resources?path="+folderName)
                .then()
                .statusCode(204);

    }
}
