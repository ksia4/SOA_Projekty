package logging;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;

@Stateless
public class SampleEJB {
    @Resource
    private EJBContext ejbContext;

    @RolesAllowed("PARKING_CONTROLLER")
    public String getPrincipalName() {
        return ejbContext.getCallerPrincipal().getName();
    }
}