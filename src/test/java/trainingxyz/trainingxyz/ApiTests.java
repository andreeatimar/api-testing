package trainingxyz.trainingxyz;

import models.Product;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    // This test displays all the existing categories;
    @Test
    public void getCategories() {
        String endpoint = "http://localhost:8888/api_testing/category/read.php";
        var response = given().when().get(endpoint).then();
        response.log().body();
    }

    // This request displays the product having the id = 2;
    // Also, this is the first test written after adding matchers and asserts
    @Test
    public void getProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        given().
                queryParam("id", 2).
                when().
                get(endpoint).
                then().
                assertThat().
                statusCode(200).
                body("id", equalTo("2")).
                body("name", equalTo("Cross-Back Training Tank")).
                body("description", equalTo("The most awesome phone of 2013!")).
                body("price", equalTo("299.00")).
                body("category_id", equalTo("2")).
                body("category_name", equalTo("Active Wear - Women"));
    }

    // This request creates a product;
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

    // This request updates an existing entry;
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

    // This request deletes an entry from the DB
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

    // This request gets an array of products in scope of testing complex response bodies
    // After the request, the test is developed by adding assertions. We also learn how to use headers
    @Test
    public void getProducts() {
        String endpoint = "http://localhost:8888/api_testing/product/read.php";
        given().
                when().
                get(endpoint).
                then().
                log().
                headers().
                assertThat().
                statusCode(200).
                header("Content-Type", equalTo("application/json; charset=UTF-8")).
                body("records.size()", greaterThan(0)).
                body("records.id", everyItem(notNullValue())).
                body("records.name", everyItem(notNullValue())).
                body("records.description", everyItem(notNullValue())).
                body("records.price", everyItem(notNullValue())).
                body("records.category_id", everyItem(notNullValue())).
                body("records.category_name", everyItem(notNullValue()));
    }

    // Deserializing response body
    @Test
    public void getDeserializedProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product actualProduct = given().
                queryParam("id", "2").
                when().
                get(endpoint).
                as(Product.class);
        assertThat(actualProduct,
                samePropertyValuesAs(expectedProduct));

    }

}

