package ru.iFellow.step;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import ru.iFellow.utils.Props;

public class RickAndMorty {

    public static final String BASE_URL = Props.props.rickAndMortyBaseUrl();

    public JSONObject getCharacterByName(String name) {
        Response response = RestAssured
                .given()
                .baseUri(BASE_URL)
                .basePath("/character")
                .queryParam("name", name)
                .get();

        response.then().statusCode(200);
        return new JSONObject(response.getBody().asString());
    }

    public JSONObject getEpisodeByUrl(String url) {
        Response response = RestAssured
                .given()
                .get(url);

        response.then().statusCode(200);
        return new JSONObject(response.getBody().asString());
    }

    public JSONObject getLocationByUrl(String url) {
        Response response = RestAssured
                .given()
                .get(url);

        response.then().statusCode(200);
        return new JSONObject(response.getBody().asString());
    }

    public JSONObject getCharacterByUrl(String url) {
        Response response = RestAssured
                .given()
                .get(url);

        response.then().statusCode(200);
        return new JSONObject(response.getBody().asString());
    }
}
