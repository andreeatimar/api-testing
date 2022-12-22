package trainingxyz;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ApiTests {

    // This test displays all the existing categories;
    @Test
    public void getCategories() {
        String endpoint = "http://localhost:80/api_testing/category/read.php";
        var response = given().when().get(endpoint).then();
        response.log().body();
    }

    // This test displays the product having the id = 2;
    @Test
    public void getProduct() {
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().
                        queryParam("id", 2).
                        when().
                        get(endpoint).
                        then();
        response.log().body();
    }

    // This test creates a product;
    @Test
    public void createProducts() {
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        String body = """
                {
                 "name": "Water Bottle",
                 "description": "Blue water bottle",
                 "price": 12,
                 "category_id": 3
                }
                """;
        var response = given().body(body).when().post(endpoint).then();
        response.log().body();
    }

    // This test updates an existing entry;
    @Test
    public void updateProduct() {
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String body = """
                { 
                "id": 1000,
                "name": "Water Bottle",
                "description": "Moonshine",
                "price": 20,
                "category_id": 3
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
        response.log().status();
    }

    // This test deletes an entry from the DB
    @Test
    public void deleteProduct() {
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = """
                {
                "id": 1000
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
        response.log().status();
    }
}
