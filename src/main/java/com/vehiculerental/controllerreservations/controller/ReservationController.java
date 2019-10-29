package com.vehiculerental.controllerreservations.controller;

import com.vehiculerental.controllerreservations.model.Reservation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "MAD des données de réservation")
@RestController
public class ReservationController {

    @ApiOperation(value = "liste de toutes les réservations")
    @RequestMapping(value = {"/reservations"}, method = RequestMethod.GET)
    public List<Reservation> reservationsList(){
        return new ApiConsumer().getReservations();
    }

    @ApiOperation(value = "Détails d'une réservation par id")
    @RequestMapping(value = {"/resevation/{id}"}, method = RequestMethod.GET)
    public Reservation reservationsById(@PathVariable int id) {
        return new ApiConsumer().getReservationById(id);
    }

    @ApiOperation(value = "Enregistrement d'une nouvelle réservation")
    @RequestMapping(value = {"/reservation/create"}, method = RequestMethod.POST)
    public Reservation createReservation(@RequestBody Reservation reservation){
        new ApiConsumer().postReservation(reservation);
        return reservation;
    }

    @ApiOperation(value = "Suppression d'une réservation")
    @RequestMapping(value = {"/reservation/delete/{id}"}, method = RequestMethod.DELETE)
    public List<Reservation> deleteReservation(@PathVariable int id){
        ApiConsumer apiConsumer = new ApiConsumer();
        apiConsumer.deleteReservation(id);
        return apiConsumer.getReservations();
    }

    @ApiOperation(value = "mise à jour d'une réservation")
    @RequestMapping(value = {"/reservation/update"},  method = RequestMethod.PUT)
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        new ApiConsumer().updateReservation(reservation);
        return reservation;
    }

    @ApiOperation(value = "Liste des immats non disponibles")
    @RequestMapping(value = {"/reservations/immat/notfree/{demandeDateDebut}/{demandeDateFin}"}, method = RequestMethod.GET)
    public List<String> getImmatNotFree(@PathVariable int demandeDateDebut, @PathVariable int demandeDateFin){
        List<String> immatNotFree = new ArrayList<>();
        ApiConsumer apiConsumer = new ApiConsumer();
        List<Reservation> reservationsNotBilled = apiConsumer.getReservationNotBilled();

        reservationsNotBilled = reservationsNotBilled
                .stream()
                .filter(reservation -> demandeDateDebut <= reservation.getDateDebut() && reservation.getDateDebut() <= demandeDateFin ||
                                       demandeDateDebut <= reservation.getDateFin()  && reservation.getDateFin() <= demandeDateFin)
                .collect(Collectors.toList());

        reservationsNotBilled.forEach(reservation -> immatNotFree.add(reservation.getImmat()));
        return immatNotFree;
    }
}
