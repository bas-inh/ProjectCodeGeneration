package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import io.swagger.model.enumeration.TransactionType;
import io.swagger.model.enumeration.UserType;
import io.swagger.repo.TransactionRepo;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.relation.Role;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Swagger2SpringBoot.class, AccountService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TransactionServiceTest {

    @MockBean
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    public void setupMock() {
        List<User> users = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        // Bas is customer
        User bas = new User();
        bas.setId(UUID.randomUUID());
        bas.setUsername("BasH");
        bas.setPassword("test123");
        bas.setFirstname("Bas");
        bas.setLastname("Hulskamp");
        bas.setDob(LocalDate.of(2000, 1, 1));
        bas.setAddress("Testweg 1");
        bas.setEmail("test@mail.com");
        bas.setPhone("+31612345678");
        bas.setRegisteredOn(OffsetDateTime.now());
        bas.setDayLimit(500.0);
        bas.setTransLimit(100.0);
        bas.setActive(true);
        bas.setUserTypes(Collections.singletonList(UserType.ROLE_CUSTOMER));
        users.add(bas);

        // Sander is employee
        User sander = new User();
        sander.setId(UUID.randomUUID());
        sander.setUsername("SanderB");
        sander.setPassword("test123");
        sander.setFirstname("Sander");
        sander.setLastname("Baak");
        sander.setDob(LocalDate.of(2000, 1, 1));
        sander.setAddress("Testweg 1");
        sander.setEmail("test1@mail.com");
        sander.setPhone("+31612345679");
        sander.setRegisteredOn(OffsetDateTime.now());
        sander.setDayLimit(500.0);
        sander.setTransLimit(100.0);
        sander.setActive(true);
        sander.setUserTypes(Collections.singletonList(UserType.ROLE_EMPLOYEE));
        users.add(sander);

        // Account Bas current
        Account basCurrent = new Account();
        basCurrent.setIban("NL01INHO0000000002");
        basCurrent.setPincode(0000);
        basCurrent.setAccountType(AccountType.CURRENT);
        basCurrent.setBalance(1000.0);
        basCurrent.setAbsLimit(-10.0);
        basCurrent.setActive(true);
        basCurrent.setUser(bas);
        accounts.add(basCurrent);

        // Account Bas savings
        Account basSavings = new Account();
        basSavings.setIban("NL01INHO0000000003");
        basSavings.setPincode(0000);
        basSavings.setAccountType(AccountType.SAVINGS);
        basSavings.setBalance(1000.0);
        basSavings.setAbsLimit(-10.0);
        basSavings.setActive(true);
        basSavings.setUser(bas);
        accounts.add(basSavings);

        // Account Sander current
        Account sanderCurrent = new Account();
        sanderCurrent.setIban("NL01INHO0000000004");
        sanderCurrent.setPincode(0000);
        sanderCurrent.setAccountType(AccountType.CURRENT);
        sanderCurrent.setBalance(10.0);
        sanderCurrent.setAbsLimit(0.0);
        sanderCurrent.setActive(true);
        sanderCurrent.setUser(sander);
        accounts.add(sanderCurrent);

        // Account Sander savings
        Account sanderSavings = new Account();
        sanderSavings.setIban("NL01INHO0000000005");
        sanderSavings.setPincode(0000);
        sanderSavings.setAccountType(AccountType.SAVINGS);
        sanderSavings.setBalance(10.0);
        sanderSavings.setAbsLimit(0.0);
        sanderSavings.setActive(true);
        sanderSavings.setUser(sander);
        accounts.add(sanderSavings);

        Transaction transBas = new Transaction();
        transBas.setTimestamp(LocalDate.now());
        transBas.setTransactionType(TransactionType.REGULAR);
        transBas.setFrom(basCurrent);
        transBas.setTo("NL01INHO0000000003");
        transBas.setAmount(50.0);
        transBas.setUserPerforming(basCurrent.getUser().getId());
        transBas.setPinCode(0000);
        transactions.add(transBas);
    }
}
