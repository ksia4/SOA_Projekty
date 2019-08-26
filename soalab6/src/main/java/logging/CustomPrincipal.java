package logging;

import org.jboss.security.SimplePrincipal;

public class CustomPrincipal extends SimplePrincipal {
    private String description;

    public CustomPrincipal(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
