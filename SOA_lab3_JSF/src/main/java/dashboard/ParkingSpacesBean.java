package dashboard;

import dao.EmployeeDao;
import dao.ParkingSpaceDao;
import enums.EmployeeRole;
import org.jboss.ejb3.annotation.SecurityDomain;
import parking.Employee;
import parking.ParkingSpace;

import javax.annotation.security.DeclareRoles;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ManagedBean(name="parkingSpaces")
@RequestScoped
@SecurityDomain("moje_jndi")
@DeclareRoles({"ADMIN","PARKING_CONTROLLER"})
public class ParkingSpacesBean {

    private ParkingSpaceDao parkingSpaceDao;
    private EmployeeDao employeeDao;
    private Employee employee;


    public ParkingSpacesBean(){
        this.parkingSpaceDao = new ParkingSpaceDao();
        this.employeeDao = new EmployeeDao();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String userLogin;
        userLogin = request.getRemoteUser();
        employee = employeeDao.getEmployeeByLogin(userLogin);
    }

    public List<ParkingSpace> getAll(){
        if("PARKING_CONTROLLER".equals(employee.getEmployeeRole()))
            return parkingSpaceDao.getSpacesByUser(employee.getEmployeeId());
        else
            return parkingSpaceDao.getAll();
    }
}
