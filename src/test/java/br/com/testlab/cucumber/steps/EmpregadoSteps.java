package br.com.testlab.cucumber.steps;

import br.com.testlab.dtos.EmpregadoDto;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EmpregadoSteps {

    @LocalServerPort
    private int port;

    @PostConstruct
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost/empregado";
    }

    @Entao("validar a busca de todos empregados")
    public void validar_a_busca_de_todos_empregados() {
        given()
            .header("Accept", "*/*")
        .when()
            .get("/findAll")
        .then()
            .statusCode(200)
            .body("[0].nrEmpregado", equalTo(100))
            .body("[0].nmEmpregado", equalTo("Dilma Roussef"))
            .body("[0].nrGerente",   equalTo(null))
            .body("[0].dsCargo",     equalTo("Presidente"))
            .body("[0].dtAdmissao",  equalTo("1998-01-13T03:00:00.000+00:00"))
            .body("[0].vlSalario",   equalTo(20000.0F))
            .body("[0].vlComissao",  equalTo(null));
    }

    @Entao("validar o cadastro de empregado")
    public void validar_o_cadastro_de_empregado() {
        EmpregadoDto empregadoDto = new EmpregadoDto();
        empregadoDto.setNrEmpregado(0);
        empregadoDto.setNmEmpregado("Nome Teste");
        empregadoDto.setNrGerente(0);
        empregadoDto.setVlSalario(new BigDecimal("10"));
        empregadoDto.setDtAdmissao(new Date());
        empregadoDto.setVlComissao(new BigDecimal("10"));
        empregadoDto.setDsCargo("Cargo Teste");

        given()
            .header("Accept", "*/*")
            .contentType(ContentType.JSON)
        .when()
            .body(empregadoDto)
            .post("/create")
        .then()
            .statusCode(200)
            .body("nrEmpregado", equalTo(0))
            .body("nmEmpregado", equalTo("Nome Teste"))
            .body("nrGerente",   equalTo(0))
            .body("dsCargo",     equalTo("Cargo Teste"))
            .body("vlSalario",   equalTo(10))
            .body("vlComissao",  equalTo(10));
    }

    @Entao("validar a busca de empregado")
    public void validar_a_busca_de_empregado() {
        given()
            .header("Accept", "*/*")
        .when()
            .params("nrEmpregado", 100)
            .get("/read")
        .then()
            .statusCode(200)
            .body("nrEmpregado", equalTo(100))
            .body("nmEmpregado", equalTo("Dilma Roussef"))
            .body("nrGerente",   equalTo(null))
            .body("dsCargo",     equalTo("Presidente"))
            .body("dtAdmissao",  equalTo("1998-01-13T03:00:00.000+00:00"))
            .body("vlSalario",   equalTo(20000.0F))
            .body("vlComissao",  equalTo(null));
    }

    @Entao("validar a alteracao de empregado")
    public void validar_a_alteracao_de_empregado() {
        EmpregadoDto empregadoDto = new EmpregadoDto();
        empregadoDto.setNrEmpregado(100);
        empregadoDto.setNmEmpregado("Nome Teste");
        empregadoDto.setNrGerente(0);
        empregadoDto.setVlSalario(new BigDecimal("10"));
        empregadoDto.setVlComissao(new BigDecimal("10"));
        empregadoDto.setDsCargo("Cargo Teste");

        given()
            .header("Accept", "*/*")
            .contentType(ContentType.JSON)
        .when()
            .body(empregadoDto)
            .put("/update")
        .then()
            .statusCode(200)
            .body("nrEmpregado", equalTo(100))
            .body("nmEmpregado", equalTo("Nome Teste"))
            .body("nrGerente",   equalTo(0))
            .body("dsCargo",     equalTo("Cargo Teste"))
            .body("vlSalario",   equalTo(10))
            .body("vlComissao",  equalTo(10));
    }

    @Entao("validar a delecao de empregado")
    public void validar_a_delecao_de_empregado() {
        given()
            .header("Accept", "*/*")
        .when()
            .params("nrEmpregado", 100)
            .delete("/delete")
        .then()
            .statusCode(200);
    }

}
