package dashboard;

import dao.EmployeeDao;
import dao.ParkingDao;
import dao.RegisteredPaymentDao;
import parking.Employee;
import parking.Parking;
import parking.RegisteredPayment;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "notification",eager = true)
@ApplicationScoped
public class Notification {
    private ParkingDao parkingDao;
    private EmployeeDao employeeDao;
    private Map<String, List<String>> messages = new HashMap<String, List<String>>();

    public Notification() {
        this.parkingDao = new ParkingDao();
        List<Parking> parkingList = parkingDao.getAll();
        for (Parking p : parkingList)
            this.messages.put(p.getLocation(), new ArrayList<String>());

        //Testowanie powiadomien, do usuniecia
        Parking notificationTest = parkingDao.get(168);
        this.messages.get(notificationTest.getLocation()).add("Achtung! Wiadomosc probna!");
        //koniec testowania

    }

    public Map<String, List<String>> getMessages() {
        return messages;
    }

    public List<String> getNotification() {
        employeeDao = new EmployeeDao();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String login = request.getRemoteUser();
        Employee employee = employeeDao.getEmployeeByLogin(login);

        try {
            Context ctx = new InitialContext();
            ConnectionFactory fact = (ConnectionFactory) ctx.lookup("java:/ConnectionFactory");
            Destination dest = (Destination) ctx.lookup("jms/queue/SOA_test");
            Connection con = fact.createConnection();
            Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer receiver = ses.createConsumer(dest);


            con.start();
            while (true) {
                Message msg = receiver.receive(100); // blokowanie (ale nie dłużej niż n ms)
                System.out.println("+++++++++++++++++++++++++++++++++++Dostalem jakies message");
                if (msg instanceof TextMessage) {
                    System.out.println("+++++++++++++++++++++++++++++++Przetwarzanie tresci");
                    TextMessage text = (TextMessage) msg;
                    String split[] = text.getText().split("_");
                    System.out.println("++++++++++++tresc: " + split[0]);
                    if (split.length == 2) {
                        List<String> l = messages.get(split[0]);
                        l.add("Nieoplacone miejsce o numerze: " + split[1]);
                    }
                } else if (msg != null) {
                    System.out.println("Received no text message");
                    List<Parking> parkingList = parkingDao.getAll();
                    List<String> l = messages.get(parkingList.get(0).getLocation());
                } else if (msg == null) {
                    break;
                }
            }
            con.close();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }


        List<String> m = new ArrayList<String>();
        if (employee.getEmployeeRole().equals("PARKING_CONTROLLER")) {
            for (String s : messages.get(employee.getParking().getLocation())) {
                m.add(s);
            }
            return m;
        } else {
            for (String key : messages.keySet()) {
                for (String s : messages.get(key)) {
                    m.add(s);
                }
            }
            return m;

        }

    }
}