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

        ParkingSpaceService hello = service.getPort(ParkingSpaceService.class);

        System.out.println(hello.changeParkingSpaceState(12,false));
        System.out.println(hello.changeParkingSpaceState(14,true));
        System.out.println(hello.changeParkingSpaceState(18,true));
        System.out.println(hello.changeParkingSpaceState(14,false));

    }

}