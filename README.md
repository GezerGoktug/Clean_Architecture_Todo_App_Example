<h1> Clean Architecture Todo App Example</h1>

**In this project, a todo app was developed using Clean Architecture in Spring Boot. The aim was to make the software development process more sustainable, testable and understandable with the layered architecture approach.**


### 🔨 **Technologies :**

- Spring Boot

- Spring Data JPA

- PostgreSQL

- Redis

- JWT

- Spring Boot Starter Validation

- Spring Boot OAuth

- Spring Pagination

- Spring Security

- Spring Boot Dotenv

- Spring Boot Actuator

- Spring Boot Test

- Docker



### 🔑 **Features:**

- **Redis Cache:** Requests are cached. Thus, faster responses are provided for subsequent requests.

- **Spring Security & OAuth & JWT:** Role-based session management was preferred with JSON Web Token (JWT) and Spring Security. Endpoints were protected. In addition, security was increased with Google OAuth.

- **Custom Authentication Provider:** By changing Spring's default username and password system, an email and password-based system has been developed.

- **Pagination:** In bulk data retrieval, a paged structure has been created using Spring Pagination, thus increasing performance.

- **Rate Limiter:** Redis and rate limiter structure have been used. In this way, excessive requests have been prevented.

- **Validation:** With Spring Validation, incoming requests have been checked before reaching the controller layer and secure data transfer has been ensured.

- **Dotenv:** Sensitive information has been pulled from the .env file and data security has been ensured.

- **Docker:** The project has been turned into a container with Docker. In this way, the project has become portable.



### 📘 **Layers:**

- **Domain:**

  - **Entities:** Entities that will cover the most basic business logic of our application.

  - **Factory:** Factory classes that produce entities in our application.

  - **Enums:** Fixed expression structures.

- **Application:**

    - **Accessors:** Interfaces that allow us to reach some things we want to reach from the outside world in an abstract way in the upper layer (for example, session information or cookies).

    - **Ds_Gateways:** Interfaces that abstract the operations we want to do in the database. This layer is independent of the DB. Also, DTOs used to meet the requests we want to send to the DB and the responses to be returned from there are located here.

    - **DTO:** DTOs used for requests coming to Interactors and for the responses to be returned from there.

    - **Exceptions:** Custom exception classes used.

    - **Interactors:** Boundary interfaces that we abstract to meet requests coming from the outside world. The classes that implement them manage the use cases that manage the main business logic.

    - **Pagination:** A pagination request and response structure has been created independently of technology.

    - **Use_Cases:** All business logic is found in these classes. They create business logic using domain objects and fabricators and also using ds gateways. A generic use case structure has been created. For example, for a use case that will create a todo, there is a use case logic that adds it not only to ourselves but also to the specified user. In addition, for the use case that does this while getting todos, if the user ID is sent, todos specific to this user are received with method overloading, if the todo ID is sent, only this todo is received.

    - **Utils:** Frequently used auxiliary classes are located here.

- **Infrastructure:**

    - **Config:** Classes that set configurations for technological details and more.

    - **Controllers:** The layer that meets API requests. It interacts with boundary interfaces.

    - **Data_Factory:** Class that performs operations with data mappers on the DB using repositories.

    - **Data_Mappers:** Our entities connected to the DB.

    - **Exceptions:** Global error catcher class. Class structures that give a general generic error response when an error occurs are located here.

    - **Handlers:** Handlers that perform some operations (handlers that are performed in the OAuth successful case or classes that provide operations on the HTTP context with these requests in the event of a request).

    - **JWT:** Classes that perform token generation operations with JSON Web Token and create an intermediate layer for protected routes using these tokens.

    - **Repositories:** Repository interfaces that allow us to perform operations in the DB with data mappers using Data JPA.

    - **Util:** Helper classes used in this layer.

- **Framework & Drivers:**

Spring starters or UI libraries can be found here.


### **Screenshots**

<img src="https://github.com/user-attachments/assets/d0d5fa92-aabc-4153-af71-ca2825330635" width=500 />
<img src="https://github.com/user-attachments/assets/0d235e22-ba1f-4672-a7e9-4935c6ab8c92" width=500 />
<img src="https://github.com/user-attachments/assets/d12df8f9-46c7-47b5-a0a3-9c43b19f7e9b" width=500 />
<img src="https://github.com/user-attachments/assets/82579cc8-57f4-42c5-ae58-bbb791bd3b55" width=500 />
<img src="https://github.com/user-attachments/assets/8b8e70d2-4faa-4066-99c8-e5adc3b107c3" width=500 />
<img src="https://github.com/user-attachments/assets/055f4bcf-9532-4186-86c1-025349883768" width=500 />


## 🔧 **Setup**

1.**Download the repository**

```
git clone https://github.com/GezerGoktug/Clean_Architecture_Todo_App_Example.git
```

2.**Enter the project directory**

```
cd Clean_Architecture_Todo_App_Example
```

3.**If Spring Boot uses a configuration manager such as Maven or Gradle, install the required dependencies with the following command. If you are using Maven:**

```
mvn clean install
```
**If you are using Gradle:**


```
gradle build
```

4.**Create a PostgreSQL database on your computer or serverless**

If Postgre SQL database is not installed on your computer, install it or create a serverless PostgreSQL database (Neon, Amazon Auora, Crunchy etc.). Obtain a database connection url.

5.**Create a Redis database on your computer or serverless**

If Redis database is not installed on your computer, install it or create a serverless Redis database (Upstash, Amazon etc.).

**Or if you have docker installed, you can create a redis database with a redis image on docker.**

```
docker run --name <CONTAINER-NAME> -p 6379:6379 -d redis
```

6.**Create an .env file with the following content in the same directory**

```
DB_PASSWORD = <YOUR_POSTGRES_DB_PASSWORD> 
DB_URL = <YOUR_POSTGRES_DB_URL>
DOCKER_DB_URL = <YOUR_POSTGRES_DB_URL_FOR_DOCKER>
DB_USERNAME = <YOUR_POSTGRES_USERNAME>
DB_NAME = <YOUR_POSTGRES_DB_NAME>
GOOGLE_CLIENT_ID = <YOUR_GOOGLE_CLIENT_ID>
GOOGLE_CLIENT_SECRET = <YOUR_GOOGLE_CLIENT_SECRET>
REDIS_HOST = <YOUR_REDIS_HOST>
DOCKER_REDIS_HOST = <YOUR_REDIS_HOST_FOR_DOCKER>
REDIS_PORT = <YOUR_REDIS_PORT>
ACCESS_TOKEN_SECRET_KEY = <YOUR_JWT_ACCESS_TOKEN_SECRET_KEY>
REFRESH_TOKEN_SECRET_KEY = <YOUR_JWT_REFRESH_TOKEN_SECRET_KEY>

```



7.**Start the application**

```
mvn spring-boot:run

```

**or if you are using Gradle:**

```
gradle bootRun

```

8.**Show in browser**

You can test the application by sending requests to `http://localhost:8080` with postman.

## 🔧 **Setup with Docker**

**NOTE**: For this process, Docker must be installed on your computer.

1.**Update the DOCKER_DB_URL variable in the .env file**

```
DOCKER_DB_URL = "jdbc:postgresql://host.docker.internal:5430/<YOUR_POSTGRES_DB_NAME>"
...
```


2.**Update the DOCKER_REDIS_HOST variable in the .env file**

```
DOCKER_REDIS_HOST = "host.docker.internal"
...

```
3.**Run docker command**

```
docker compose up -d
```

4.**Test in browser**

You can test the application by sending requests to `http://localhost:8080` with postman.

## Contribute 🤝

- You can use the [Issues](https://github.com/GezerGoktug/Clean_Architecture_Todo_App_Example) tab for bug reports and suggestions.
- If you want to contribute to the project, create a fork and submit a pull request.
