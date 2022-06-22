package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.UserType;
import io.swagger.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsersApiController.class)
class UsersApiControllerTest {

    private static final int SKIP = 0;
    private static final int LIMIT = 1;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private UserService userService;

    @Mock
    private User user;

    @Mock
    private UserDTO userDTO;

    @Test
    void getAllUserShouldReturnJsonArrayContainingOneElement() throws Exception {
        when(userService.getAll(SKIP, LIMIT)).thenReturn(List.of());
        this.mockMvc.perform(get("/users?skip=0&limit=1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "", password = "")
    void addUserShouldReturnANonNullObjectAndStatusCreated() throws Exception {
        when(userService.getAll(SKIP, LIMIT)).thenReturn(List.of());
        this.mockMvc.perform(get("/users?skip=0&limit=1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private User createUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setUserTypes(List.of(UserType.ROLE_CUSTOMER));
        return user;
    }

}