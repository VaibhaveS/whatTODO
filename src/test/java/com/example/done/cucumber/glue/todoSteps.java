package com.example.done.cucumber.glue;

import com.example.done.cucumber.SpringIntegrationTest;
import com.example.done.model.TodoItem;
import com.example.done.repo.TodoRepo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration // Don't ask
public class todoSteps extends SpringIntegrationTest {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private TestRestTemplate restTemplate;

    private String caller; // input

    private ResponseEntity<String> response; // output

    @Given("I populate tasks")
    public void popTasks() {

        todoRepo.save(new TodoItem(1L, "play football", false, null));
        todoRepo.save(new TodoItem(2L, "play cricket", false, null));

    }

    @When("I fire a GET request to fetch all tasks")
    public void fireGetTasks() {
        response = restTemplate
                .exchange("http://localhost:8080/todo", HttpMethod.GET, null, String.class);
    }

    @Then("I should get a response with HTTP status code {int}")
    public void shouldGetResponseWithHttpStatusCode(int statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @When("I fire a GET request to fetch all tasks of a particular user {int}")
    public void fireGetTasksUser(int userid) {
        response = restTemplate
                .exchange("http://localhost:8080/todo/" + Integer.toString(userid), HttpMethod.GET, null, String.class);
    }

}
