import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
//achtung: do usuniecia
@ManagedBean(name = "Losowanie")
@SessionScoped
public class Losowanie {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void validateAge(FacesContext context, UIComponent comp, Object value){
        Integer age = Integer.parseInt((String) value);

        if(age < 0 || age > 120){
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("Podaj prawdziwy wiek");
            context.addMessage(comp.getClientId(context),message);
        }
    }

    public String wyslij() {
        if (Math.random() < 0.2)
            return "OK";
        else
            return "NOT_OK";
    }
}
