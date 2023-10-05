package com.web;

import com.web.controller.ServerController;
import com.web.dto.User;
import com.web.http.UserClient;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WebclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebclientApplication.class, args);
	}

	@Bean
	RestClient restClient(RestClient.Builder builder){
		return builder.baseUrl("http://localhost:8081/server").build();
	}

	@Bean
	ApplicationRunner applicationRunner(RestClient restClient, UserClient userClient) {
		return args -> {

			//rest client
			ResponseEntity<Void> postResponse = restClient.post().uri("").body(new User(4,"test1","test1", 24)).retrieve().toBodilessEntity();
			System.out.println("rest client : POST");
			System.out.println(postResponse);

			ResponseEntity<User> getByIdResponse = restClient.get().uri("/{id}", 1).retrieve().toEntity(User.class);
			System.out.println("rest client : GET : {id}");
			System.out.println(getByIdResponse);

			ResponseEntity<List<User>> getAllResponse = restClient.get().uri("").retrieve().toEntity(new ParameterizedTypeReference<List<User>>(){});
			System.out.println("rest client : GET");
			System.out.println(getAllResponse.getBody());

			//Using Http interfaces
			var users = userClient.users();
			System.out.println("HTTP interface : GET");
			System.out.println(users);

			var user = userClient.user(2);
			System.out.println("HTTP interface : GET : {id}");
			System.out.println(user);
		};
	}

	@Bean
	UserClient userClient(RestClient restClient) {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(UserClient.class);
	}

	@PostConstruct
	public void generateData(){
		var users = ServerController.users;
		User user1 = new User(1,"user one", "user1", 30);
		User user2 = new User(2,"user two", "user2", 31);
		User user3 = new User(3,"user three", "user3", 32);

		users.add(user1);
		users.add(user2);
		users.add(user3);
	}

}
