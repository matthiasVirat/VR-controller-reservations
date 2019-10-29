package com.vehiculerental.controllerreservations.controller;

import com.vehiculerental.controllerreservations.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiConsumer {

    private RestTemplate restTemplate = new RestTemplate();

    List<Reservation> getReservations(){
        Reservation[] reservations = restTemplate.getForObject("http://localhost:9083/reservations", Reservation[].class);
        return new ArrayList<>(Arrays.asList(reservations));
    }

    Reservation getReservationById(int id){
        return restTemplate.getForObject("http://localhost:9083/reservation/"+id, Reservation.class);
    }

    Reservation postReservation(Reservation reservation){
        return restTemplate.postForObject("http://localhost:9083/reservation/create", reservation, Reservation.class);
    }

    void deleteReservation(int id) {
        restTemplate.delete("http://localhost:9083/reservation/delete/"+id);
    }

    void updateReservation(Reservation reservation){
        restTemplate.put("http://localhost:9083/reservation/update", reservation);
    }

    List<Reservation> getReservationNotBilled(){
        Reservation[] reservations = restTemplate.getForObject("http://localhost:9083/reservations/notbilled", Reservation[].class);
        return new ArrayList<>(Arrays.asList(reservations));
    }
}
