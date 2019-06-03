package webservice.parking;

import javax.xml.ws.Endpoint;

public class ParkingSpaceServicePublisher {
    public static void main(String[] args){
        Endpoint.publish("http://localhost:8080/ParkingSpaceService"
                ,new ParkingSpaceServiceImpl());
    }
}
