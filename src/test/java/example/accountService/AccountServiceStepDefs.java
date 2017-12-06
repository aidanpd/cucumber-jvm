package example.accountService;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.equalTo;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig.NumberReturnType;
import io.restassured.response.Response;
import java.math.BigDecimal;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Profile("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    loader = SpringBootContextLoader.class,
    classes = AccountServiceApp.class
)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountServiceStepDefs {

  @LocalServerPort
  int port;

  @Before
  public void setup() {
    RestAssured.port = port;
  }

  private Response res;

  @Given("^An account (\\d+) with a balance of (\\d+)$")
  public void account_with_balance(long accNum, BigDecimal bal) throws Exception {

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

    res = with()
        .body(withdrawal)
        .when()
        .put("/api/account/" + accNum);
  }

  @Then("^Account (\\d+) has balance (\\d+)$")
  public void account_has_balance(long accNum, BigDecimal bal) throws Exception {

    res = get("/api/account/" + accNum);
    res.then()
        .body("bal", equalTo(bal));
  }

  @Then("^A (\\d+) status is returned")
  public void status_is_returned(int expectedStatus) throws Exception {

    res.then().statusCode(expectedStatus);
  }

}
