package service;

import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.service.AccountService;
import com.rosivanyshyn.service.implMySQL.AccountServiceImpl;
import org.junit.jupiter.api.Test;

public class AccountServiceTest {
    AccountService accountService = new AccountServiceImpl();

    @Test
    public void createAccountTest()
    {
        Account account = Account.builder()
                .id(0L)
                .role("user")
                .firstName("Oleg")
                .lastName("Shevchuk")
                .email("oleg_shevchuk@gmail.com")
                .password("fjtn285ty2")
                .state(true)
                .build();

        accountService.createAccount(account);
        accountService.deleteAccount(account);
    }
}
