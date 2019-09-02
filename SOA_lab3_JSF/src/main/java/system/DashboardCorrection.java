package system;



import com.sun.xml.internal.messaging.saaj.util.Base64;
import dao.EmployeeDao;
import dao.ParkingSpaceDao;
import dao.RegisteredPaymentDao;
import enums.ParkingSpaceState;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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
    private String spaceToPunishId;
    private String punishedPlate;

    public void correct() throws IOException {
        if(spaceId == null || ticketId == null)
            return;
        ParkingSpace space1 = parkingSpaceDao.get(Integer.parseInt(spaceId));
        RegisteredPayment payment2 = space1.getPayment();
        String splitTicket[] = ticketId.split(" ");
        RegisteredPayment payment1 = registeredPaymentDao.get(Integer.parseInt(splitTicket[1]));
        ParkingSpace space2 = payment1.getParkingSpace();

        space1.setPayment(payment1);
        payment1.setParkingSpace(space1);

        checkSpaceForCorrectState(space1);

        if(space2 != null) {
            space2.setPayment(payment2);
            payment2.setParkingSpace(space2);
            checkSpaceForCorrectState(space2);
        } else{
            payment2.setParkingSpace(null);
        }

        parkingSpaceDao.save(space1);
        parkingSpaceDao.save(space2);
        registeredPaymentDao.save(payment1);
        registeredPaymentDao.save(payment2);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("dashboard.xhtml");
    }

    private void checkSpaceForCorrectState(ParkingSpace space){
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(space.getPayment().getEndTime())) {
            if(space.getPayment().getPlate() != null)
                space.setParkingSpaceState(ParkingSpaceState.PAID);
            else
                space.setParkingSpaceState(ParkingSpaceState.WAITING_FOR_PAYMENT);
        } else {
            space.setParkingSpaceState(ParkingSpaceState.UNPAID);
        }
    }

    public void punish() throws IOException {
        if(spaceToPunishId == null)
            return;
        ParkingSpace space = parkingSpaceDao.get(Integer.parseInt(spaceToPunishId));
        RegisteredPayment payment = space.getPayment();
        space.setParkingSpaceState(ParkingSpaceState.PUNISHED);
        parkingSpaceDao.update(space);
        if(payment.getPlate() == null){
            payment.setPlate(punishedPlate);
            registeredPaymentDao.update(payment);
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("dashboard.xhtml");
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

    public String getSpaceToPunishId() {
        return spaceToPunishId;
    }

    public void setSpaceToPunishId(String spaceToPunishId) {
        this.spaceToPunishId = spaceToPunishId;
    }

    public String getPunishedPlate() {
        return punishedPlate;
    }

    public void setPunishedPlate(String punishedPlate) {
        this.punishedPlate = punishedPlate;
    }
}
