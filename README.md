
<a name="installation">
 
#### Installation

1. Clone this repository

```sh
$ git clone https://github.com/VaibhaveS/whatTODO.git
```

2. Start RabbitMQ server

```sh

$ rabbitmq-server
```

3. Open mySQL server

```sh
 edit application.properties file with schema name, url and password.
```

4. Run "DoneApplication.java" class

5. Send requests using postman

<ul>
  <li> fetch all todos - GET request http://localhost:8080/todo </li>
  <li> fetch todos of a particular user - GET request http://localhost:8080/todo/{userId} </li>
  <li> add a todo - POST request http://localhost:8080/todo 
  <br/>
    BODY : { <br/>
     &nbsp; "name" <br/>
     &nbsp; "id" //automatically assigned <br/>
     &nbsp; "status" //optional (default false) <br/>
     &nbsp; "userid" //optional (default null) <br/>
    }
  </li>
  <li> update a task - PUT request http://localhost:8080/todo
  <br/>
    BODY : { <br/>
     &nbsp; "name" <br/>
     &nbsp; "id"
     &nbsp; "status" //optional (default false) <br/>
     &nbsp; "userid" //optional (default null) <br/>
    }
  </li>
  <li> assign a task to a user - PUT request http://localhost:8080/todo/{id}/{userId} </li>
  <li> delete a task - DELETE request http://localhost:8080/todo/{taskId} </li>
  <li> write todos from csv file to DB - POST request http://localhost:8080/todo/batch </li>
</ul>


