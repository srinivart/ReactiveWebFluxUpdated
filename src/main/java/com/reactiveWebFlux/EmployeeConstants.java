package com.reactiveWebFlux;

public class EmployeeConstants {

	public static final String GET_ALL_EMPLOYEES = "/employees";
	
	public static final String FIND_EMPLOYEE_BY_ID = "/employees/find/{id}";
	
	public static final String ADD_NEW_EMPLOYEE = "/employees/add";
	
	public static final String UPDATE_EMPLOYEE_BY_ID = "/employees/update/{id}";
	
	public static final String DELETE_EMPLOYEE_BY_ID = "/employees/delete/{id}";
	
}



/*



1) http://localhost:8088/employees

2) http://localhost:8088/employees/find/2

3) http://localhost:8088/employees/add
{"firstName":"Sam","designation":"Senior Developer","salary":"95000"}

4)  http://localhost:8088/employees

5) http://localhost:8088/employees/update
{"firstName":"Vennela","designation":"Senior Developer","salary":"95000"}

6) http://localhost:8088/employees/delete/5


7)http://localhost:8088/employees/update/1
{"firstName":"Sree","designation":"Senior Developer","salary":"95000"}


http://localhost:8088/employees/update/1




{"firstName":"Vennela","designation":"Senior Developer","salary":"95000"}



/employees

/employees/find/{employeeId}

/employees/add

/employees/update/{employeeId}

/employees/delete/{employeeId}

*/