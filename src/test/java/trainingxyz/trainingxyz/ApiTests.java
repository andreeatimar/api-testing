package trainingxyz.trainingxyz;

import models.Product;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ApiTests {

    // This test displays all the existing categories;
    @Test
    public void getCategories() {
        String endpoint = "http://localhost:8888/api_testing/category/read.php";
        var response = given().when().get(endpoint).then();
        response.log().body();
    }

    // This test displays the product having the id = 2;
    @Test
    public void getProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
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
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
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
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
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
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = """
                {
                "id": 1000
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
        response.log().status();
    }

    @Test
    public void createChallengeProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product(
                "Experimental product",
                "random description",
                10,
                3
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product(
                "Sweatband",
                "challenge product",
                5,
                3
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateChallengeProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
        String body = """
                {
                "id": 22,
                "price": 6
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void getSweatBand() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        var response =
                given().
                        queryParam("id", 22).
                        when().
                        get(endpoint).
                        then();
        response.log().body();
    }

    @Test
    public void deleteSweatBand() {
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = """
                {
                "id" : 22
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }
}

