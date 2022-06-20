package io.swagger.bankapi.junit5.service;

import io.swagger.model.dto.UserDTO;
import io.swagger.model.entity.User;
import io.swagger.repo.UserRepo;
import io.swagger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setup() {
//        userService = new UserService();
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