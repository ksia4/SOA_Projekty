package dao;

import parking.Employee;

import javax.persistence.Query;
import java.util.List;

public class EmployeeDao extends AbstractDao<Employee> {

    public EmployeeDao(){super();}

    @Override
    public Employee get(int id) {
        return em.find(Employee.class,id);
    }

    public Employee getEmployeeByUsername(String login){
        return em.find(Employee.class,login);
    }

    @Override
    public List<Employee> getAll() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }
}
