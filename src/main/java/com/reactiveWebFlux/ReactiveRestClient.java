package com.reactiveWebFlux;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

import static com.reactiveWebFlux.EmployeeConstants.*;


public class ReactiveRestClient {

	Logger logger = LoggerFactory.getLogger(ReactiveRestClient.class);

	private WebClient webclient;

	public ReactiveRestClient(WebClient webclient) {
		this.webclient = webclient;
	}

	public List<Employee> getAllEmployees() {
		return webclient.get()
						.uri(GET_ALL_EMPLOYEES)
						.retrieve()
						.bodyToFlux(Employee.class).collectList().block();

	}

	public Employee findEmployeeById(int employeeId) {
		try {
			return webclient.get()
							.uri(FIND_EMPLOYEE_BY_ID, employeeId)
							.retrieve()
							.bodyToMono(Employee.class)
							.block(); 																												// synchronous
		} catch (WebClientResponseException ex) {
			logger.error("Error Response Code is {} and the response body is {} ", ex.getRawStatusCode(),
					ex.getResponseBodyAsString());
			logger.error("WebClientResponseException in retrieveEmployeeById", ex);
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception in retrieveEmployeeById", ex);
			throw ex;
		}
	}

	public Employee addNewEmployee(Employee employee) {

		try {
			return webclient.post()
					.uri(ADD_NEW_EMPLOYEE)
					.bodyValue(employee)
					.retrieve()
					.bodyToMono(Employee.class)
					.block();
			
		} catch (WebClientResponseException ex) {
			logger.error("Error Response Code is {} and the response body is {} ", ex.getRawStatusCode(),
					ex.getResponseBodyAsString());
			logger.error("WebClientResponseException in addNewEmployee", ex);
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception in addNewEmployee", ex);
			throw ex;
		}
	}

	public Employee updateEmployee(int employeeId, Employee employee) {

		try {
			return webclient.put()
							.uri(UPDATE_EMPLOYEE_BY_ID, employeeId)
							.bodyValue(employee)
							.retrieve()
							.bodyToMono(Employee.class).block();
			
		} catch (WebClientResponseException ex) {
			logger.error("Error Response Code is {} and the response body is {} ", ex.getRawStatusCode(),
					ex.getResponseBodyAsString());
			logger.error("WebClientResponseException in updateEmployee", ex);
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception in updateEmployee", ex);
			throw ex;
		}
	}

	public String deleteEmployeeById(int employeeId) {
		return webclient.delete()
						.uri(DELETE_EMPLOYEE_BY_ID, employeeId)
						.retrieve()
						.bodyToMono(String.class)
						.block();

	}
	
	public Mono<Employee> getAllEmployeesResp() {
		return webclient.get()
						.uri(GET_ALL_EMPLOYEES)
						.accept(MediaType.APPLICATION_JSON)
						.exchangeToMono(response -> {
					         if (response.statusCode().equals(HttpStatus.OK)) {
					             return response.bodyToMono(Employee.class);
					         }
					         return response.bodyToMono(Employee.class);
					         
					     });
						  
	}
	
	
	
	public Employee testRetrieve(int employeeId ) {
		return webclient.get()
				.uri(FIND_EMPLOYEE_BY_ID, employeeId)
				.retrieve()
				.bodyToMono(Employee.class)
				.block(); 
	}
	
	public ResponseEntity<Employee> testRetrieveResponseEntity(int employeeId ) {
		return webclient.get()
				.uri(FIND_EMPLOYEE_BY_ID, employeeId)
				.retrieve()
				.toEntity(Employee.class)
				.block(); 
	}
	
	
	public Mono<Employee> testExchangeToMono(int employeeId) {
		System.out.println("--> exchangeToMono ->");
		return webclient.get()
				.uri(FIND_EMPLOYEE_BY_ID, employeeId)
				.accept(MediaType.APPLICATION_JSON)
				.exchangeToMono(response ->{
					System.out.println("--> exchangeToMono inside ->");
					if (response.statusCode().equals(HttpStatus.OK)) {
						 System.out.println("Success 200 ->");
			             return response.bodyToMono(Employee.class);
			         }
					System.out.println("Inside Mono ->");
					return response.bodyToMono(Employee.class);
				});
	}
	

	public Mono<Employee> testExchangeToMono1(int employeeId) {
		System.out.println("--> exchangeToMono ->");
		Mono<Employee> emp = webclient.get()
				.uri(FIND_EMPLOYEE_BY_ID, employeeId)
				.accept(MediaType.APPLICATION_JSON)
				.exchangeToMono(response ->{
					System.out.println("--> exchangeToMono inside ->");
					if (response.statusCode().equals(HttpStatus.OK)) {
						 System.out.println("Success 200 ->");
			             return response.bodyToMono(Employee.class);
			         }
					System.out.println("Inside Mono ->");
					return response.bodyToMono(Employee.class);
				});
		System.out.println("Outside ->");
		return emp;
	}
	// --> exchangeToMono ->
	// Outside ->
	
	
	
	public Mono<Object> testExchangeToMono2(int employeeId) {
		System.out.println("--> exchangeToMono ->");
		Mono<Object> emp = webclient.get()
				.uri(FIND_EMPLOYEE_BY_ID, employeeId)
				.accept(MediaType.APPLICATION_JSON)
				.exchangeToMono(response -> response.bodyToMono(Employee.class));
		System.out.println("Outside ->");
		   return emp;
		 //return Mono.just(emp);
		 //Employee emp1 = new Employee("sree","Developer","65000");
		 //return Mono.just(emp1);
	}
	
}




// https://stackoverflow.com/questions/64650820/spring-webflux-5-3-0-webclient-exchangetomono	
// https://github.com/spring-projects/spring-framework/issues/26023	
