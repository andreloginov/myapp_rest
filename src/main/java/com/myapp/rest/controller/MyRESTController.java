package com.myapp.rest.controller;

import com.myapp.rest.entity.Employee;
import com.myapp.rest.exception_handling.NoSuchEmployeeException;
import com.myapp.rest.service.EmployeeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    private final EmployeeService employeeService;

    public MyRESTController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {

        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee showSingleEmployee(@PathVariable int id) {
        Employee employee = employeeService.getEmpFromId(id);

        if (employee == null) {
            throw new NoSuchEmployeeException("There's no employee with id = " +
                    id + " id data base.");
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {

        if (employeeService.deleteEmployee(id)) {
           return "An employee whose id is equal to " + id + " has been deleted.";
        } else {
            throw new NoSuchEmployeeException("An employee whose id is equal to " + id + " was not found in the Database");
        }
    }
}
