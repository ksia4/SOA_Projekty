package webservice.client;

import webservice.parking.ParkingSpaceService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class ParkingSpaceServiceClient {
    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:8080/ParkingSpaceServiceImpl?wsdl");

        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://parking.webservice/", "ParkingSpaceServiceImplService");

        Service service = Service.create(url, qname);

        ParkingSpaceService parkingSpaceService = service.getPort(ParkingSpaceService.class);

        System.out.println(parkingSpaceService.changeParkingSpaceState(67,true));


    }

}
