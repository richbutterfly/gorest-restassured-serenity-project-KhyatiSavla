package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.constants.Path;
import com.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class UserSteps {

    @Step("Creating user with name : {0}, email : {1}, gender : {2} and status {3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) { // create one user

        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given()
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .contentType(ContentType.JSON)
                .when()
                .body(userPojo)
                .post(Path.USERS)
                .then().log().all();

    }
    @Step("Getting the user information with user ID {0}")
    public HashMap<String, Object> getUserInfoById(int userId) {

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .pathParam("userID", userId)
                .when()
                .get(Path.USERS + EndPoints.GET_SINGLE_USER_BY_ID)
                .then().statusCode(200).extract().path("");
    }
    @Step("Updating user with name : {0}, email : {1}, gender : {2} and status {3}")
    public ValidatableResponse updateUser (int userId, String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .contentType(ContentType.JSON)
                .pathParam("userID", userId)
                .when()
                .body(userPojo)
                .put(Path.USERS + EndPoints.UPDATE_USER_BY_ID)
                .then().log().all();

    }

    @Step("Deleting user information with userId : {0}")
    public ValidatableResponse deleteUser (int userId) {
        return SerenityRest.given()
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .contentType(ContentType.JSON)
                .pathParam("userID", userId)
                .when()
                .delete(Path.USERS + EndPoints.DELETE_USER_BY_ID)
                .then().log().all();

    }
    @Step("Getting user information with userId : {0}")
    public ValidatableResponse getUserById(int userId) {
        return SerenityRest.given()
                .pathParam("userID", userId)
                .when()
                .get(Path.USERS + EndPoints.GET_SINGLE_USER_BY_ID)
                .then().log().all();
    }
    }
