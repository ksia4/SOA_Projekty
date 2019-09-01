package system;



import com.sun.xml.internal.messaging.saaj.util.Base64;
import dao.EmployeeDao;
import dao.ParkingSpaceDao;
import dao.RegisteredPaymentDao;
import org.jboss.ejb3.annotation.SecurityDomain;
import parking.Employee;
import parking.ParkingSpace;
import parking.RegisteredPayment;

import javax.annotation.security.DeclareRoles;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "correction", eager = true)
@RequestScoped
@SecurityDomain("moje_jndi")
@DeclareRoles({"ADMIN","PARKING_CONTROLLER"})
public class DashboardCorrection {
    private EmployeeDao employeeDao = new EmployeeDao();
    private ParkingSpaceDao parkingSpaceDao = new ParkingSpaceDao();
    private RegisteredPaymentDao registeredPaymentDao = new RegisteredPaymentDao();
    private Employee user;
    private String spaceId;
    private String ticketId;


//    public List<String> getUsers(){
//
//        String login;
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        login = request.getRemoteUser();
//        user = employeeDao.getEmployeeByLogin(login);
//
//
//        //String sql;
//        List<Employee> employeeList = new ArrayList<Employee>();
//        if (user.getEmployeeRole().equals("PARKING_CONTROLLER"))
//            employeeList.add(user);
//        else
//            employeeList.addAll(employeeDao.getAll());
//
//        List<String> ret = new ArrayList<String>();
//        for (Employee e : employeeList){
//            ret.add(e.getLogin());
//        }
//        return ret;
//    }


    public void correct(){
        ParkingSpace space1 = parkingSpaceDao.get(Integer.parseInt(spaceId));
        RegisteredPayment payment2 = space1.getPayment();
        String splitTicket[] = ticketId.split(" ");
        RegisteredPayment payment1 = registeredPaymentDao.get(Integer.parseInt(splitTicket[1]));
        ParkingSpace space2 = payment1.getParkingSpace();

        space1.setPayment(payment1);
        payment1.setParkingSpace(space1);

        space2.setPayment(payment2);
        payment2.setParkingSpace(space2);

        parkingSpaceDao.save(space1);
        parkingSpaceDao.save(space2);
        registeredPaymentDao.save(payment1);
        registeredPaymentDao.save(payment2);

    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceId(){
        return this.spaceId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketId(){
        return this.ticketId;
    }
}
