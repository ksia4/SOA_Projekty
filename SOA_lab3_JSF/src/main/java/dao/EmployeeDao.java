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

    public Employee getEmployeeByLogin(String login){
        String jpql = "select e from Employee e where e.login = :l ";
        Employee result = em.createQuery(jpql,Employee.class)
                .setParameter("l",login).getSingleResult();
        return result;
    }

    @Override
    public List<Employee> getAll() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }
}
