package com.reactiveWebFlux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;

import reactor.core.publisher.Mono;



public class ReactiveRestClientTest {
	
	Gson gson = new Gson();
	
	private static final String baseUrl = "http://localhost:8088/";
	private WebClient webclient = WebClient.create(baseUrl);
	
	ReactiveRestClient reactiveRestClient = new ReactiveRestClient(webclient);
	
	
	@Test
	void getAllEmployees() {
		List<Employee> employeeList = reactiveRestClient.getAllEmployees();
		System.out.println(employeeList);
		for(Employee emp : employeeList) {
			emp.getFirstName();
			emp.getLastName();
			emp.getDesignation();
			emp.getSalary();
		}
		assertTrue(employeeList.size()>0);	
	}

	
	@Test
	void getEmployeeById() {
		int employeeId = 2;
		Employee employee = reactiveRestClient.findEmployeeById(employeeId);
		
		assertEquals("Sahi", employee.getFirstName());
	}
	
	
	
	@Test
	void addNewEmployee() {
		Employee employee = new Employee("Sam","Senior Developer","95000");
		Employee employee1 = reactiveRestClient.addNewEmployee(employee);
		System.out.println("employee1 :" + employee1);
		assertTrue(employee1.getId()!=0);
	}
	//{"firstName":"Sam","designation":"Senior Developer","salary":"95000"}
	
	
	
	@Test 
	void updateEmployee() {
		Employee employee = new Employee("Sree","Senior Developer","95000");
		Employee updatedEmployee = reactiveRestClient.updateEmployee(1, employee);
		
		assertEquals("Sree", updatedEmployee.getFirstName());
		assertEquals("Senior Developer", updatedEmployee.getDesignation());
	}
	//{"firstName":"Sree","designation":"Senior Developer","salary":"95000"}
	
	
	
	@Test
	 void deleteEmployeeById() {
		Employee employee = new Employee("John","Developer","65000");
		Employee employee1 = reactiveRestClient.addNewEmployee(employee);
		System.out.println("employee1 :" + employee1);
		
		String response = reactiveRestClient.deleteEmployeeById(employee1.getId());
		
		System.out.println("***->"+ response);
		System.out.println("***->"+ employee1.getId());
		String expectedMessage = "Deleted employee with id: "+employee1.getId();
		 
		 assertEquals(expectedMessage,response);
		
	}
	
	@Test
	void testRetrieve() {
		
		Employee e = reactiveRestClient.testRetrieve(3);
		System.out.println(gson.toJson(e));
		System.out.println(gson.toJson(e.getFirstName()));
		//{"id":3,"firstName":"Teju","designation":"QA","salary":"75000"}
		//"Teju"
	}
	
	@Test
	void testEndpiontRerieveEntity() {
		
		ResponseEntity<Employee> e = reactiveRestClient.testRetrieveResponseEntity(3);
		System.out.println(gson.toJson(e));
	  // {"status":200,"headers":{"Content-Type":["application/json"],"Transfer-Encoding":["chunked"],"Date":["Tue, 25 May 2021 15:18:22 GMT"]},"body":{"id":3,"firstName":"Teju","designation":"QA","salary":"75000"}}
            
		System.out.println("Status ->"+e.getStatusCode());
		System.out.println("Status code Value -> "+ e.getStatusCodeValue());
		System.out.println("Headers -> "+ e.getHeaders());
		System.out.println("Headers-> contentType -> "+ e.getHeaders().getContentType());
		System.out.println("Body -> "+ e.getBody());
	
	}
	
	
	
		
		/*
	 
	 	Status ->200 OK
		Status code Value -> 200
		Headers -> [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Tue, 25 May 2021 15:30:58 GMT"]
		Headers-> contentType -> application/json
		Body -> Employee [id=3, firstName=Teju, lastName=null, Designation=QA, email=null]
		 
		*/
	
	



/*
 
 
 {
   "status":200,
   "headers":{
      "Content-Type":[
         "application/json"
      ],
      "Transfer-Encoding":[
         "chunked"
      ],
      "Date":[
         "Tue, 25 May 2021 15:18:22 GMT"
      ]
   },
   "body":{
      "id":3,
      "firstName":"Teju",
      "designation":"QA",
      "salary":"75000"
   }
}


 */

        @Test
        void testMonoToExchange() {
        	Mono<Employee> emp = reactiveRestClient.testExchangeToMono(3);

        	System.out.println(gson.toJson(emp));
        }
        
        
        @Test
        void testMonoToExchange1() {
        	Mono<Employee> emp = reactiveRestClient.testExchangeToMono1(3);

        	System.out.println(gson.toJson(emp));
        }
        
      // {"mapper":{},"source":{}}
        
        
        @Test
        void testMonoToExchange2() {
        	Mono<Object> emp = reactiveRestClient.testExchangeToMono2(3);

        	System.out.println(gson.toJson(emp));
        }
        // {"mapper":{},"source":{}}
        // {"value":{"mapper":{},"source":{}}}
        // with hardcodig 
        // {"value":{"id":0,"firstName":"sree","designation":"Developer","salary":"65000"}}
        
        
   }    