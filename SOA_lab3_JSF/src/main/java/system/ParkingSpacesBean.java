package system;

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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="parkingSpaces")
@RequestScoped
@SecurityDomain("moje_jndi")
@DeclareRoles({"ADMIN","PARKING_CONTROLLER"})
public class ParkingSpacesBean {

    private ParkingSpaceDao parkingSpaceDao = new ParkingSpaceDao();
    private EmployeeDao employeeDao = new EmployeeDao();
    private RegisteredPaymentDao registeredPaymentDao = new RegisteredPaymentDao();
    private Employee employee;
    private ParkingSpace incorrectSpace;
    private RegisteredPayment paymentToCorrection;

    public ParkingSpacesBean() throws IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String userLogin;
        userLogin = request.getRemoteUser();
        employee = employeeDao.getEmployeeByLogin(userLogin);
        if(employee == null){
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect("index.xhtml");
        }
    }

    public List<ParkingSpace> getAll(){
        if("PARKING_CONTROLLER".equals(employee.getEmployeeRole()))
            return parkingSpaceDao.getSpacesByUser(employee.getEmployeeId());
        else
            return parkingSpaceDao.getAll();
    }


    public String getUserName(){
        return employee.getFirstName() + " " + employee.getLastName();
    }

    public String getLogin(){
        return employee.getLogin();
    }

    public String getRole(){
        return employee.getEmployeeRole();
    }

    public String getParking(){
        if(employee.getEmployeeRole().equals("ADMIN"))
            return "ALL LOCATIONS";
        else if(employee.getParking() == null)
            return "NO LOCATION ASSIGNED";
        else
            return employee.getParking().getLocation();
    }

    public ParkingSpace getIncorrectSpace() {
        return incorrectSpace;
    }

    public void setIncorrectSpace(ParkingSpace incorrectSpace) {
        this.incorrectSpace = incorrectSpace;
    }

    public List<String> getSpacesList() {
        List<ParkingSpace> spaces;
        if(employee.getEmployeeRole().equals("ADMIN"))
            spaces = parkingSpaceDao.getAllNonEmptySpaces();
        else
            spaces = parkingSpaceDao.getSpacesToCorrection(employee.getEmployeeId());
        List<String> spacesList = new ArrayList<String>();
        for(ParkingSpace s : spaces){
            spacesList.add(Integer.toString(s.getParkingSpaceId()));
        }
        return spacesList;
    }

    public List<String> getTicketsList() {
        List<RegisteredPayment> tickets;
        if(employee.getEmployeeRole().equals("ADMIN"))
            tickets = registeredPaymentDao.getAll();
        else if(employee.getParking() == null)
            return new ArrayList<String>(0);
        else
            tickets = registeredPaymentDao.getTicketsToCorrection(employee.getParking().getParkingId());
        List<String> ticketList = new ArrayList<String>();
        for(RegisteredPayment t : tickets){
            String plate = t.getPlate() == null ? "???" : t.getPlate();
            String space = t.getParkingSpace() == null ?
                            "???" : Integer.toString(t.getParkingSpace().getParkingSpaceId());
            ticketList.add("ID: " + Integer.toString(t.getPaymentId())+
                            " Space: " + space +
                            " Plate: " + plate);
        }
        return ticketList;
    }

    public void setPaymentToCorrection(RegisteredPayment paymentToCorrection) {
        this.paymentToCorrection = paymentToCorrection;
    }

    public RegisteredPayment getPaymentToCorrection(){
        return this.paymentToCorrection;
    }
}
