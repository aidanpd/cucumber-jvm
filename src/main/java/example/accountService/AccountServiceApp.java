package example.accountService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class AccountServiceApp {

  private Map<Long, Account> mockAccountsDatabase = new HashMap<>();

  public static void main(String[] args) {
    SpringApplication.run(AccountServiceApp.class, args);
  }

  @RequestMapping(value = "/api/account/{id}", method = GET, produces = "application/json; charset=UTF-8")
  Account getAccount(@PathVariable("id") long id) {

    return this.mockAccountsDatabase.get(id);
  }

  @RequestMapping(value = "/api/account/{id}", method = POST, produces = "application/json; charset=UTF-8")
  Account createAccount(@PathVariable("id") long id, @RequestBody String body) {

    mockAccountsDatabase.put(id, new Account(id, new BigDecimal(body)));
    return this.mockAccountsDatabase.get(id);
  }

  @RequestMapping(value = "/api/account/{id}", method = PUT, produces = "application/json; charset=UTF-8")
  ResponseEntity<?> debitAccount(@PathVariable("id") long id, @RequestBody String body) {

    final Account account = this.mockAccountsDatabase.get(id);

    final BigDecimal debitAmount = new BigDecimal(body);
    if (account.bal.compareTo(debitAmount) >= 0) {
      account.bal = account.bal.subtract(debitAmount);
      return new ResponseEntity<>(this.mockAccountsDatabase.get(id), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }
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
