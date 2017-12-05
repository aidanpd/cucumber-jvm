package example.accountService;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.equalTo;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig.NumberReturnType;
import java.math.BigDecimal;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    loader = SpringBootContextLoader.class,
    classes = AccountServiceApp.class
)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountServiceStepDefs {

  @LocalServerPort
  int port;

  @Given("^Account (\\d+) with balance (\\d+)$")
  public void account_with_balance(long accNum, BigDecimal bal) throws Exception {
    RestAssured.port = this.port;
    RestAssured.config = RestAssured.config()
        .jsonConfig(jsonConfig().numberReturnType(NumberReturnType.BIG_DECIMAL));

    with()
        .body(bal.intValue())
        .when()
        .post("/api/account/1234")
        .then().body("bal", equalTo(200));
  }

  @When("^I withdraw (\\d+) from account (\\d+)$")
  public void withdrawal_from_account(BigDecimal withdrawal, long accNum) throws Exception {

    with()
        .body(withdrawal)
        .when()
        .put("/api/account/" + accNum)
        .then().body("bal", equalTo(BigDecimal.valueOf(100.0)));
  }

  @Given("^Account (\\d+) has balance (\\d+)$")
  public void account_has_balance(long accNum, BigDecimal bal) throws Exception {

    get("/api/account/" + accNum)
        .then()
        .body("bal", equalTo(bal));
  }

}
