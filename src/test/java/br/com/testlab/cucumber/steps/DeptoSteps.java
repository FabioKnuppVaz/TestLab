package br.com.testlab.cucumber.steps;

import br.com.testlab.dtos.DeptoDto;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeptoSteps {

    @LocalServerPort
    private int port;

    @PostConstruct
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost/depto";
    }

    @Entao("validar a busca de todos departamentos")
    public void validar_a_busca_de_todos_departamentos() {
        given()
            .header("Accept", "*/*")
        .when()
            .get("/findAll")
        .then()
            .statusCode(200)
            .body("[0].nmDepto",     equalTo("Presidência"))
            .body("[0].nrDepto",     equalTo(1))
            .body("[0].vlOrcamento", equalTo(20000.0F))
            .body("[0].dsLocal",     equalTo("São Paulo"));
    }

    @Entao("validar o cadastro de departamento")
    public void validar_o_cadastro_de_departamento() {
        DeptoDto deptoDto = new DeptoDto();
        deptoDto.setNmDepto("Nome Teste");
        deptoDto.setNrDepto(0);
        deptoDto.setVlOrcamento(new BigDecimal("0"));
        deptoDto.setDsLocal("Local Teste");

        given()
            .header("Accept", "*/*")
            .contentType(ContentType.JSON)
        .when()
            .body(deptoDto)
            .post("/create")
        .then()
            .statusCode(200)
            .body("nmDepto",     equalTo("Nome Teste"))
            .body("nrDepto",     equalTo(0))
            .body("vlOrcamento", equalTo(0))
            .body("dsLocal",     equalTo("Local Teste"));
    }

    @Entao("validar a busca de departamento")
    public void validar_a_busca_de_departamento() {
        given()
            .header("Accept", "*/*")
        .when()
            .params("nrDepto", 1)
            .get("/read")
        .then()
            .statusCode(200)
            .body("nmDepto",     equalTo("Presidência"))
            .body("nrDepto",     equalTo(1))
            .body("vlOrcamento", equalTo(20000.0F))
            .body("dsLocal",     equalTo("São Paulo"));
    }

    @Entao("validar a alteracao de departamento")
    public void validar_a_alteracao_de_apartamento() {
        DeptoDto deptoDto = new DeptoDto();
        deptoDto.setNmDepto("Nome Teste");
        deptoDto.setNrDepto(1);
        deptoDto.setVlOrcamento(new BigDecimal("0"));
        deptoDto.setDsLocal("Local Teste");

        given()
            .header("Accept", "*/*")
            .contentType(ContentType.JSON)
        .when()
            .body(deptoDto)
            .put("/update")
        .then()
            .statusCode(200)
            .body("nmDepto",     equalTo("Nome Teste"))
            .body("nrDepto",     equalTo(1))
            .body("vlOrcamento", equalTo(0))
            .body("dsLocal",     equalTo("Local Teste"));
    }

    @Entao("validar a delecao de departamento")
    public void validar_a_delecao_de_apartamento() {
        given()
            .header("Accept", "*/*")
            .params("nrDepto", 50)
        .when()
            .delete("/delete")
        .then()
            .statusCode(200);
    }

}
