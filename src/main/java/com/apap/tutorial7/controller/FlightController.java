package com.apap.tutorial7.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.service.FlightService;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @PostMapping(value = "add")
    private FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
        return flightService.addFlight(flight);
    }
    
    @PutMapping(value = "/update/{flightId}")
    private String updateFlightSubmit(@PathVariable(value = "flightId") long flightId,
    		@RequestParam(value = "destination", required = false) String destination,
    		@RequestParam(value = "origin", required = false) String origin,
    		@RequestParam(value = "time", required = false) Date time) {
    	FlightModel flight = flightService.getFlightDetailById(flightId);
    	
    	if(destination != null) flight.setDestination(destination);
    	if(origin != null) flight.setOrigin(origin);
    	if (time != null) flight.setTime(time);
    	flightService.updateFlight(flightId, flight);
    	return "flight update success";
    }
    
    @GetMapping(value = "/view/{flightNumber}")
    private FlightModel flightView(@PathVariable(value = "flightNumber") String flightNumber) {
        FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return flight;
    }
    
    @GetMapping(value = "/all")
    private List<FlightModel> flightViewAll() {
    	return flightService.getAllFlight();
    }
    
    @DeleteMapping(value = "/delete")
    private String deleteFlight(@RequestParam(value = "flightId") long flightId) {
        FlightModel flight = flightService.getFlightDetailById(flightId);
        flightService.deleteFlight(flight);
        return "flight has been deleted";
    }
}