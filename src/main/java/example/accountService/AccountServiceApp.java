package example.accountService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class AccountServiceApp {

  // mock accounts database
  private Map<Long, Account> accounts = new HashMap<>();

  public static void main(String[] args) {
    SpringApplication.run(AccountServiceApp.class, args);
  }

  @RequestMapping(value = "/api/account/{id}", method = GET, produces = "application/json; charset=UTF-8")
  Account getAccount(@PathVariable("id") long id) {

    return this.accounts.get(id);
  }

  @RequestMapping(value = "/api/account/{id}", method = POST, produces = "application/json; charset=UTF-8")
  Account createAccount(@PathVariable("id") long id, @RequestBody String body) {
    System.out.println("create acc body " + body);
    accounts.put(id, new Account(id, new BigDecimal(body)));
    return this.accounts.get(id);
  }

  @RequestMapping(value = "/api/account/{id}", method = PUT, produces = "application/json; charset=UTF-8")
  Account debitAccount(@PathVariable("id") long id, @RequestBody String body) {
    System.out.println("withdrawal body " + body);
    final Account account = this.accounts.get(id);
    account.bal = account.bal.subtract(new BigDecimal(body));
    return this.accounts.get(id);
  }

  class Account {

    public long id;
    public BigDecimal bal;

    public Account(long id, BigDecimal bal) {
      this.id = id;
      this.bal = bal;
    }
  }
}
