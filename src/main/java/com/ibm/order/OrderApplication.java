package com.ibm.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;

/**
* <h1>Order Application </h1>

* Part of the Java Full Stack bootcamp lab exercise using
* Java/Spring framework, MongoDB, React.
* <p>
* Creates a REST API to order food from a restaurant menu.
* <p>
* (For more information, see see <a href="https://github.ibm.com/wleonar/ibmfutureskills">GitHub Repo</a>)
* <p>
* Project structure:
* <ul>
* <li> <i>Model</i> - POJO representing entities, such as an order. Order is mapped to database collection.
* <li> <i>Endpoint</i> - makes a request to menu API to retrieve more information.
* <li> <i>Service</i> - handles business logic.
* <li> <i>Rest Controller</i> - maps uri requests to Service calls.
* </ul>
* 
* <p>
* Swagger: http://localhost:<port>/swagger-ui.html
* 

*
* @author  Ella Blackledge
* @version 1.0
* @since   2021-05-20 
*/

@SpringBootApplication
@EnableCircuitBreaker
@EnableSwagger2
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	   public Docket productApi() {
	      return new Docket(DocumentationType.SWAGGER_2).select()
	         .apis(RequestHandlerSelectors.basePackage("com.ibm.order")).build();
	   }
}
