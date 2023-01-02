package com.myapp.rest.dao;

import com.myapp.rest.entity.Employee;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> getAllEmployees() {
        return entityManager.createQuery("from Employee", Employee.class).getResultList();
    }

    @Override
    public void saveEmployee(Employee employee) {
        entityManager.merge(employee);
    }

    @Override
    public Employee getEmpFromId(int id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public boolean deleteEmployee(int id) {
        if (entityManager.find(Employee.class, id) != null) {
            entityManager.createQuery("delete from Employee e where e.id =: varId")
                    .setParameter("varId", id).executeUpdate();
            return true;
        } else {
            return false;
        }


    }
}
