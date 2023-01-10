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

import javax.transaction.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration // Don't ask
@Transactional
public class todoSteps extends SpringIntegrationTest {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private TestRestTemplate restTemplate;

    private String caller; // input

    private Boolean prevStatus;

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

    @When("I fire a PUT request to update a task with id {int} to assign to user with id {int}")
    public void firePutAssignUser(int taskid, int userid){
        todoRepo.getById((long) taskid).setUserId(userid);
    }

    @Then("The given task with {int} must be assigned to user with {int}")
    public void assertTaskAssigned(int taskid, int userid){
        assert todoRepo.getById((long) taskid).getUserId() == userid;
    }

    @When("I fire a PUT request to update a task with id to toggle task status {int}")
    public void firePutStatusUpd(int taskid) {
        TodoItem todoItem = todoRepo.getById((long) taskid);
        prevStatus = todoItem.isDone();
        todoItem.setDone(!todoItem.isDone());
    }


    @Then("The given task with {int} must have updated status")
    public void assertToggleStatus(int taskid) {
        assert todoRepo.getById((long) taskid).isDone() == !(prevStatus);
    }

    @Given("my csv file named {string} exists")
    public void myCsvFileNamedCsvNameExists(String name) {
        File f = new File("src/main/resources/"+name);

    }

    @When("I fire a PUT request to populate tasks from CSV")
    public void firePutCSV() {
        response = restTemplate
                .exchange("http://localhost:8080/todo/batch", HttpMethod.POST, null, String.class);
        
    }

    @Then("I should have all tasks from {string} in the repository")
    public void checkTasksFromCSV(String name) throws FileNotFoundException {

    }
}



