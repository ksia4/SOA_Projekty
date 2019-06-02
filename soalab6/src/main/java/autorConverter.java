import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
// IT SHOULD BE DELETED, UNUSED CLASS
public class autorConverter implements Converter {
    private static Wyszukiwarka wyszukiwarka = new Wyszukiwarka();

    public Autor getAsObject(FacesContext contex, UIComponent component, String value){
        return wyszukiwarka.find(value);
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

        return ((Autor) value).getNazwa();
    }

}
