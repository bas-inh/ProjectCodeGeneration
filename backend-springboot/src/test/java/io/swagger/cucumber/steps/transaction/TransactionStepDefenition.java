package io.swagger.cucumber.steps.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.cucumber.steps.BaseStepDefinitions;
import io.swagger.model.dto.TransactionDTO;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpHeaders;

public class TransactionStepDefenition extends BaseStepDefinitions implements En {

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIxIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTM0MTk1NDEsImV4cCI6MTY4NDk3NjQ5M30.EwhnHxzCVH-mDeoaKgnhsgx4RQ2BgvzCnRFqPoIKV4o";
    private static final String VALID_TOKEN_EMPLOYEE = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LWVtcGxveWVlMSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfRU1QTE9ZRUUifSx7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiaWF0IjoxNjUzNDE5NTE2LCJleHAiOjE2ODQ5NzY0Njh9.wz_6cKBM9mmAYmv2FVGGj8UvsL1mXXNMWnwtAMXp-fE";
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LWVtcGxveWVlMSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiaWF0IjoxNjUzMjMxMzYzLCJleHAiOjE2NTMyMzQ5NjN9.eDIOKqTxayyyVP1QLOCC2QwJXrMg1M8EM0gMaVP1P64";
    private static final String INVALID_TOKEN = "invalid";

//    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;

    private Integer status;
    private TransactionDTO transactionDTO;

    private String token;

    public TransactionStepDefenition() {
//        Given("^the user has a valid token for role \"([^\"]*)\"$", (String role) -> {
//            switch (role) {
//                case "user":
//                    token = VALID_TOKEN_USER;
//                    break;
//                case "employee":
//                    token = VALID_TOKEN_EMPLOYEE;
//                    break;
//                default:
//                    throw new IllegalArgumentException("No such role");
//            }
//        });
//        And("^the user has a valid new transaction$", () -> {
//            transactionDTO = new TransactionDTO();
//
//            transactionDTO.setFrom("USER_ACCOUNT_3_IBAN");
//            transactionDTO.setTo("USER_ACCOUNT_4_IBAN");
//            transactionDTO.setAmount(10.00);
//        });
//        When("^the user makes a post request to the transactions endpoint$", () -> {
//            httpHeaders.clear();
//            httpHeaders.add("Authorization", "Bearer " + token);
//            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//            request = new HttpEntity<>(mapper.writeValueAsString(transactionDTO), httpHeaders);
//
//            response = restTemplate.postForEntity(getBaseUrl() + "/transactions", request, String.class);
//            status = response.getStatusCodeValue();
//        });
//        Then("^the system returns a status of (\\d+)$", (Integer arg0) -> {
//        });
    }
}
