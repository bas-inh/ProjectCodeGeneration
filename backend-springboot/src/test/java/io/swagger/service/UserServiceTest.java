package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {Swagger2SpringBoot.class})
@AutoConfigureMockMvc
class UserServiceTest {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDTO userDTO;

    @Mock
    private User user;

    @MockBean
    private UserService userService;


    @BeforeEach
    void setup() {
        user = new User();

        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setDob(LocalDate.of(2003, 7, 23));
        user.setAddress("address");
        user.setEmail("email");
        user.setPhone("phone");
        user.setRegisteredOn(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        user.setDayLimit(50000.00);
        user.setTransLimit(5000.00);
        user.setActive(true);
    }

    @Test
    void addUserShouldReturnNonNullUserObject() {
        assertNotNull(userService.addUser(user));
    }
}