package br.com.testlab.steps;

import br.com.testlab.dtos.EmpregadoDto;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Date;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class EmpregadoSteps {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Dado("validar a busca de todos empregados")
    public void validarAbuscaDeTodosEmpregados() {
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

    @Dado("validar a busca do empregado")
    public void validarABuscaDoEmpregado() {
        given()
            .header("Accept", "*/*")
        .when()
            .get("/empregado/findById?nrEmpregado=100")
        .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("nrEmpregado", equalTo(100))
            .body("nmEmpregado", equalTo("Dilma Roussef"))
            .body("dsCargo", equalTo("Presidente"))
            .body("vlSalario", equalTo(20000.0F));
    }

    @Dado("validar a delecao do empregado")
    public void validarADelecaoDoEmpregado() {
        given()
            .header("Accept", "*/*")
        .when()
            .delete("/empregado/deleteById?nrEmpregado=100")
        .then()
            .statusCode(202);
    }

    @Dado("validar a alteracao do empregado")
    public void validarAAlteracaoDoEmpregado() {
        EmpregadoDto empregadoDto = new EmpregadoDto();
        empregadoDto.setNmEmpregado("João");
        empregadoDto.setNrGerente(10);
        empregadoDto.setDtAdmissao(new Date());
        empregadoDto.setDsCargo("QA");
        empregadoDto.setVlSalario(new BigDecimal("2000"));
        empregadoDto.setVlComissao(new BigDecimal("1000"));

        given()
                .contentType(ContentType.JSON)
                .header("Accept", "*/*")
                .when()
                .body(empregadoDto)
                .post("/empregado/insert")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Dado("validar a insercao do empregado")
    public void validarAInsercaoDoEmpregado() {
        EmpregadoDto empregadoDto = new EmpregadoDto();
        empregadoDto.setNmEmpregado("João");
        empregadoDto.setNrGerente(10);
        empregadoDto.setDtAdmissao(new Date());
        empregadoDto.setDsCargo("QA");
        empregadoDto.setVlSalario(new BigDecimal("2000"));
        empregadoDto.setVlComissao(new BigDecimal("1000"));

        given()
            .contentType(ContentType.JSON)
            .header("Accept", "*/*")
        .when()
             .body(empregadoDto)
        .post("/empregado/insert")
             .then()
        .statusCode(HttpStatus.CREATED.value());
    }

}