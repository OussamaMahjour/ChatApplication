package com.oussama.SocialMedia.user_service;

import com.oussama.SocialMedia.user_service.repository.Repository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;



import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceApplicationTests {



	@LocalServerPort
	private Integer port;

	@Container
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2")
			.withUsername("mysql")
			.withPassword("root")
			.withDatabaseName("user_service_db");
	@BeforeAll
	static void beforeAll() {
		mySQLContainer.start();

	}

	@AfterAll
	static void afterAll() {
		mySQLContainer.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);

	}

	@Autowired
	private Repository userRepository;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost:" + port;
	}

	@Order(2)
	@Test
	void shouldGetAllUsers(){
		get("/api/v1/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("size()",is(10))
				.log()
				.ifValidationFails();

	}



	@Order(3)
	@Test
	void shouldCreatUser(){
		String user = """
				{
						    "username":"MimicDom",
						    "firstname": "Jacki",
						    "lastname": "shane",
						    "about": "Senior  developer",
						    "phone": 1234567898,
						    "email": "jacki.shane@example.com",
						    "gender": "MALE",
						    "birthday": "1945-06-11"
						}
				""";
		given()
				.when()
				.contentType("application/json")
				.body(user)
				.post("/api/v1/users")
				.then()
				.statusCode(200)
				.body("username",equalTo("MimicDom"))
				.body("firstname",equalTo("Jacki"))
				.body("lastname",equalTo("shane"))
				.body("about",equalTo("Senior  developer"))
				.body("phone",equalTo(1234567898))
				.body("email",equalTo("jacki.shane@example.com"))
				.body("gender",equalTo("MALE"))
				.body("birthday",equalTo("1945-06-11"))
				.log()
				.ifValidationFails();

	}

	@Test
	@Order(4)
	void shouldUpdateUser(){
		String user = """
				{
				        "username": "BugHunter3000",
				        "firstname": "John",
				        "lastname": "Doe",
				        "about": "Software senior developer",
				        "phone": 1234567890,
				        "email": "john.doe@example.com",
				        "gender": "MALE",
				        "birthday": "1995-06-15"
				    }
				""";
		given()
				.log()
				.ifValidationFails()
				.when()
				.body(user)
				.contentType("application/json")
				.put("/api/v1/users")
				.then()
				.statusCode(200)
				.body("username",equalTo("BugHunter3000"))
				.body("firstname",equalTo("John"))
				.body("lastname",equalTo("Doe"))
				.body("about",equalTo("Software senior developer"))
				.body("phone",equalTo(1234567890))
				.body("email",equalTo("john.doe@example.com"))
				.body("gender",equalTo("MALE"))
				.body("birthday",equalTo("1995-06-15"))
				.log().ifValidationFails();

	}


	@Order(5)
	@Test
	void shouldDeleteUser(){
		String user = """
				{
				        "username": "BugHunter3000",
				        "firstname": "John",
				        "lastname": "Doe",
				        "about": "Software developer",
				        "phone": 1234567890,
				        "email": "john.doe@example.com",
				        "gender": "MALE",
				        "birthday": "1995-06-15"
				    }
				""";
		given()
				.when()
				.body(user)
				.contentType("application/json")
				.delete("/api/v1/users")
				.then()
				.statusCode(200)
				.log()
				.ifValidationFails();
	}

	@Order(6)
	@Test
	void shouldGetUserByUsername(){
		String username = "BugHunter3000";

		given()
				.when()
				.contentType("application/json")
				.get("/api/v1/users/"+username)
				.then()
				.statusCode(200)
				.body("username",equalTo(username));

	}



}
