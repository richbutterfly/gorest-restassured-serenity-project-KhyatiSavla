package com.gorest.gorestinfo;

import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.collection.IsMapContaining.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {

    static int userId;
    static String name = TestUtils.getRandomValue() + "sanket";
    static String email = TestUtils.getRandomValue() + "sanket@gmail.com";
    static String gender = "male";
    static String status = "active";

    @Steps
    UserSteps steps;


    @Title("This will create a new user")
    @Test
    public void test001_CreateUser() {
        ValidatableResponse response = steps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
        userId = response.log().all().extract().path("id");
        System.out.println("Created user Id is :" + userId);
    }

    @Title("This will get user by user ID")
    @Test
    public void test002_ReadUser() {
        HashMap<String, Object> userMap = steps.getUserInfoById(userId);
        Assert.assertThat(userMap, hasValue(name));
    }


    @Title("Update the user information and verify the updated information")
    @Test
    public void test003_updateUser() {
        name = "Sanket" +  TestUtils.getRandomValue();
        email = "sanket@gmail.com" + TestUtils.getRandomValue();

        steps.updateUser(userId, name, email, gender, status).statusCode(200);
        HashMap<String, Object> userMap = steps.getUserInfoById(userId);
        Assert.assertThat(userMap, hasValue(userId));

    }

    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test05_DeleteUserAndVerify() {
        steps.deleteUser(userId).statusCode(204);

        steps.getUserById(userId).statusCode(404);
    }

}
