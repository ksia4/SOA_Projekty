package dao;

import parking.Employee;
import parking.ParkingSpace;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class EmployeeDao extends AbstractDao<Employee>{

    public EmployeeDao(){super();}

    @Override
    public Employee get(int id) {
        return em.find(Employee.class,id);
    }

    @Override
    public List<Employee> getAll() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }
}
