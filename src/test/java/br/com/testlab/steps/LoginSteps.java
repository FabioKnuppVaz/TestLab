package br.com.testlab.steps;

import io.cucumber.java.pt.Dado;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginSteps {

    @Dado("validar a busca de todos empregados")
    public void validarAbuscaDeTodosEmpregados() {
        RestAssured.baseURI = "http://localhost:8080";

        given()
            .header("Accept", "*/*")
            .when()
            .get("/empregado/all")
            .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].nrEmpregado", equalTo(100))
            .body("[0].nmEmpregado", equalTo("Dilma Roussef"))
            .body("[0].dsCargo", equalTo("Presidente"))
            .body("[0].vlSalario", equalTo(20000.0F));
    }

}