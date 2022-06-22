package io.swagger.cucumber.steps.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import io.swagger.cucumber.steps.BaseStepDefinitions;
import io.swagger.model.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;

@Slf4j
public class UserStepDefinitions extends BaseStepDefinitions implements En {

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6W10sImlhdCI6MTY1MzMxMTc0NiwiZXhwIjoxNjg0ODQ3NzQ2fQ.itSjs-evCYi2P7JAKwT4DY8u5RIASTghoaeQOa33v_s";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOltdLCJpYXQiOjE2NTMzMTE4MjQsImV4cCI6MTY4NDg0NzgyNH0.heZJFGgEEdaUvEhbjnbK7PFfC_BfxOMIvmRq8fjvwMs";
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6W10sImlhdCI6MTY1MzMxMTkwNSwiZXhwIjoxNjUzMzExOTA1fQ.mKFrXM15WCXVNbSFNpqYix_xsMjsH_M31hiFf-o7JXs";
    private static final String INVALID_TOKEN = "invalid";
    private static final int SKIP = 0;
    private static final int LIMIT = 1;

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private ResponseEntity<String> response;

    @Mock
    private UserDTO dto;


    private final ObjectMapper mapper = new ObjectMapper();

    public UserStepDefinitions() {

        When("^I call the users endpoint$", () -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(dto), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/users" + "?skip=" + SKIP + "&limit=" + LIMIT, request, String.class);
        });

        Then("^I receive a http-status of (\\d+)$", (Integer httpStatus) -> {
            Assertions.assertSame(httpStatus, response.getStatusCodeValue());
        });

        And("^Get a List<User> of length (\\d+)$", (Integer expectedLength) -> {
            Integer actual = JsonPath.read(response.getBody(), "$.size()");
            Assertions.assertEquals(expectedLength, actual);
        });

        Given("^I have a valid JWT$", () -> {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String token = jsonObject.getString("token");
            Assertions.assertTrue(token.startsWith("ey"));
        });
        And("^Get a non-null UserDTO object$", () -> {
            dto = mapper.readValue(new File(response.getBody()), UserDTO.class);
            Assertions.assertNotNull(dto);
        });

    }
}
