package dao;

import parking.Employee;
import parking.ParkingSpace;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeeDao extends AbstractDao<Employee>{

    public EmployeeDao(){super();}

    @Override
    public Employee get(int id) {
        return em.find(Employee.class,id);
    }
}
