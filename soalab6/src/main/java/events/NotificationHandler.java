package events;

import dao.ParkingSpaceDao;
import dao.RegisteredPaymentDao;
import parking.RegisteredPayment;

import javax.ejb.Singleton;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

@Singleton
public class NotificationHandler {
    private ParkingSpaceDao parkingSpaceDao = new ParkingSpaceDao();
    private RegisteredPaymentDao registeredPaymentDao = new RegisteredPaymentDao();
//    EntityManagerFactory factory = Persistence.createEntityManagerFactory("project-parking");
//    EntityManager em;
//    int i = 0;

//    @Inject
//    private JMSMessageProducer jmsMessageProducer;


    public void sendJMS(RegisteredPayment payment) {
        System.out.println("SEND++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++!!!");

        String text = "Alarm!!! miejsce " + payment.getParkingSpace().getParkingSpaceId();

        try {
            Context ctx = new InitialContext();
            ConnectionFactory fact = (ConnectionFactory) ctx.lookup("java:/ConnectionFactory");
            Destination dest = (Destination) ctx.lookup("jms/queue/SOA_test");
            Connection con = fact.createConnection();
            Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer sender = ses.createProducer(dest);


            con.start();
            TextMessage msg = ses.createTextMessage();
            msg.setObjectProperty("STREFA", payment.getParking().getLocation());
            msg.setText(text);
            System.out.println("Sending Message: " + msg.getText());
            sender.send(msg);
            con.close();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void start () {
        while (true) {
            List<RegisteredPayment> result = registeredPaymentDao.getExpiredPayments();
            if (result != null) {
                for (RegisteredPayment payment : result) {
                    sendJMS(payment);
                    payment.setAlert(true);
                    registeredPaymentDao.update(payment);
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
