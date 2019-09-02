package system;



import com.sun.xml.internal.messaging.saaj.util.Base64;
import dao.EmployeeDao;
import org.jboss.ejb3.annotation.SecurityDomain;
import parking.Employee;

import javax.annotation.security.DeclareRoles;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "password", eager = true)
@RequestScoped
@SecurityDomain("moje_jndi")
@DeclareRoles({"ADMIN","PARKING_CONTROLLER"})
public class PasswordBean {
    private EmployeeDao employeeDao = new EmployeeDao();
    private Employee user;
    private String newPassword;
    private String repeatedPassword;
    private String userPassChange;

    public List<String> getUsers(){ //albo usunac albo dziedziczenie

        String login;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        login = request.getRemoteUser();
        user = employeeDao.getEmployeeByLogin(login);


        List<Employee> employeeList = new ArrayList<Employee>();
        if (user.getEmployeeRole().equals("PARKING_CONTROLLER"))
            employeeList.add(user);
        else
            employeeList.addAll(employeeDao.getAll());

        List<String> ret = new ArrayList<String>();
        for (Employee e : employeeList){
            ret.add(e.getLogin());
        }
        return ret;
    }


    public void changePassword(){
        if(!newPassword.equals(repeatedPassword) || newPassword.isEmpty())
            return;//tutaj jakis komentarz moze dac
        if (userPassChange.equals(user.getLogin()) || user.getEmployeeRole().equals("ADMIN")) {
            try {
                String password = "haslo";

                MessageDigest md = MessageDigest.getInstance("MD5");

                byte[] hashInBytes = md.digest(newPassword.getBytes(StandardCharsets.UTF_8));

                StringBuilder sb = new StringBuilder();
                for (byte b : hashInBytes) {
                    sb.append(String.format("%02x", b));
                }
                System.out.println(sb.toString());

                String passwordHash = sb.toString();


                Employee userChangePassword = employeeDao.getEmployeeByLogin(userPassChange);
                userChangePassword.setPassword(passwordHash);
                employeeDao.update(userChangePassword);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        newPassword = null;
    }

    //zmienic nazewnictwo bo oczy bolo
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getUserPassChange() {
        return userPassChange;
    }

    public void setUserPassChange(String userPassChange) {
        this.userPassChange = userPassChange;
    }
}
