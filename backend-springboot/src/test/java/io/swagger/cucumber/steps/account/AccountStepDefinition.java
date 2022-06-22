package io.swagger.cucumber.steps.account;


import io.swagger.cucumber.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



public class AccountStepDefinition extends BaseStepDefinitions{

    private final String baseUrl = "http://localhost:8080/BankAPI/";
    // Token is valid for one and a half year
    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJGbHVmZnlVbmljb3JuNzciLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0NVU1RPTUVSIn1dLCJpYXQiOjE2NTU4NTk1NTIsImV4cCI6MTcwODA5MzI3Mn0.lGBvnY2p_J0zFIVV9fdHX7W-V85MrmUn77_fuiTBVMY";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZCIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ1VTVE9NRVIifSx7ImF1dGhvcml0eSI6IlJPTEVfRU1QTE9ZRUUifV0sImlhdCI6MTY1NTg1OTUxMSwiZXhwIjoxNzA4MDkzMjMxfQ.IkNPFINJhtfN5lzRFVawYQ4F3HnT0ipQ5T4qtUTikgc";
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6W10sImlhdCI6MTY1MzMxMTkwNSwiZXhwIjoxNjUzMzExOTA1fQ.mKFrXM15WCXVNbSFNpqYix_xsMjsH_M31hiFf-o7JXs";
    private static final String INVALID_TOKEN = "invalid";


    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static final String VALID_IBAN = "NL01INHO0000000001";
    private static final String INVALID_IBAN = "NOTVALIDIBAN0001";




    private ResponseEntity<String> accountByIban;
    @Given("I want an account and i provide a valid jwt")
    public void iHaveValidJwtToGetOneAccountWithSpecificIban() {
        Assertions.assertTrue(VALID_TOKEN_USER.startsWith("ey"));
    }

    @When("I call endpoint with get request to get one account")
    public void iCallEndpointWithGetRequestToGetOneAccount() {

        httpHeaders.clear();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", VALID_TOKEN_ADMIN);
        accountByIban = restTemplate.exchange(
                baseUrl + "getByIban/" + VALID_IBAN , HttpMethod.GET, new HttpEntity<>(httpHeaders),
                String.class);
    }




}
