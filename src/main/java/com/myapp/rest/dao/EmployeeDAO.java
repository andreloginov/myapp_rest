package com.myapp.rest.dao;

import com.myapp.rest.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    public List<Employee> getAllEmployees();
    public void saveEmployee(Employee employee);
    public Employee getEmpFromId(int id);
    public boolean deleteEmployee(int id);
}
