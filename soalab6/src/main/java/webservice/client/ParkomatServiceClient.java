package webservice.client;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import parking.PaymentRegistration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

public class ParkomatServiceClient {
    public static void main(String[] args){
        PaymentRegistration p = new PaymentRegistration();
        p.setParkingId(264);
//        p.setPlate("XD4567");
        p.setPlate("KWA10RH");
        p.setTimeInHours(3);


        try {

            ClientRequest request = new ClientRequest(
                    "http://localhost:8080/soalab5_war/rest/parkomat/registerPayment");//bylo rest
            request.accept("application/json");
            String input = "{\"parkingId\":"+p.getParkingId()
                    +",\"plate\":\""+ p.getPlate()
                    +"\",\"timeInHours\":"+p.getTimeInHours()+"}";

            //String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
            request.body("application/json", input);

            ClientResponse<String> response = request.post(String.class);

            if (response.getStatus() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus() + " "
                        + response.getResponseStatus());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new ByteArrayInputStream(response.getEntity().getBytes())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    }


