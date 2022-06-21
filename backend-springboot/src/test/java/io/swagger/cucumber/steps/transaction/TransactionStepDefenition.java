package io.swagger.cucumber.steps.transaction;

import io.cucumber.java8.En;
import io.swagger.cucumber.steps.BaseStepDefinitions;

public class TransactionStepDefenition extends BaseStepDefinitions implements En {
    public TransactionStepDefenition() {

        Given("^I have valid jwt to create new transaction$", () -> {
        });
        When("^I call endpoint with post request to create new transaction$", () -> {
        });
        Then("^I receive a status of (\\d+)$", (Integer arg0) -> {
        });
        And("^I receive new created transaction$", () -> {
        });


        Given("^I have valid jwt to get one transaction with specific id$", () -> {
        });
        When("^I call endpoint with get request to get one transaction$", () -> {
        });
        Then("^I receive http code (\\d+) ok for one transaction$", (Integer arg0) -> {
        });
        And("^I receive one transaction with the specified id$", () -> {
        });


        Given("^I have valid jwt to update transaction$", () -> {
        });
        When("^I call endpoint with put request to update transaction$", () -> {
        });
        And("^I receive the updated transaction$", () -> {
        });


        Given("^I have valid jwt to delete transaction$", () -> {
        });
        When("^I call endpoint with delete request to delete transaction$", () -> {
        });
        And("^The transaction has been deleted$", () -> {
        });
    }
}
