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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
//    private List<ParkingSpace> spacesList = new ArrayList<ParkingSpace>();
//    private List<RegisteredPayment> ticketsList = new ArrayList<RegisteredPayment>();


    public ParkingSpacesBean(){
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

//    public void correctTicket(){    //zmienic nazwe
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("space = " + incorrectSpace.getParkingSpaceId());
//        System.out.println("payment = " + paymentToCorrection.getParkingSpace().getParkingSpaceId());
//        incorrectSpace.setPayment(paymentToCorrection);
//        paymentToCorrection.setParkingSpace(incorrectSpace);
//        parkingSpaceDao.save(incorrectSpace);
//        registeredPaymentDao.save(paymentToCorrection);
//        System.out.println("++++++++++++++++++++++++++++++++DONE");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//    }

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
        return employee.getParking().getLocation();
    }

    public ParkingSpace getIncorrectSpace() {
        return incorrectSpace;
    }

    public void setIncorrectSpace(ParkingSpace incorrectSpace) {
        this.incorrectSpace = incorrectSpace;
    }

    public List<String> getSpacesList() {
        List<ParkingSpace> spaces = parkingSpaceDao.getAll();
        List<String> spacesList = new ArrayList<String>();
        for(ParkingSpace s : spaces){
            spacesList.add(Integer.toString(s.getParkingSpaceId()));
        }
        return spacesList;
    }

    public List<String> getTicketsList() {
        List<RegisteredPayment> tickets = registeredPaymentDao.getAll();
        List<String> ticketList = new ArrayList<String>();
        for(RegisteredPayment t : tickets){
            ticketList.add(Integer.toString(t.getPaymentId()));
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
