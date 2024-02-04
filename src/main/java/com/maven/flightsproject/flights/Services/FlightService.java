package com.maven.flightsproject.flights.Services;

import com.maven.flightsproject.flights.Entities.Flight;
import com.maven.flightsproject.flights.FlightsApplication;
import com.maven.flightsproject.flights.Repository.IFlightDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
@Service
public class FlightService {

    @Autowired
    private IFlightDal flightDal;
    private final String baseUrl = "https://localhost:8080/flight/api/flights";

    public FlightService(IFlightDal flightDal) {
        this.flightDal = flightDal;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void saveFlightData() {
        RestTemplate restTemplate = new RestTemplate();
        FlightsApplication response = restTemplate.getForObject(baseUrl, FlightsApplication.class);
    }

    public Flight findFlightByCriteria(String departureAirport, String arrivalAirport, String departureTime, String returnTime) {
        List<Flight> allFlights = findAllFlights();

        for (Flight flight : allFlights) {
            if (Objects.equals(flight.getDepartureAirport(), departureAirport) &&
                    Objects.equals(flight.getArrivalAirport(), arrivalAirport) &&
                    Objects.equals(flight.getDepartureTime(), departureTime) &&
                    Objects.equals(flight.getReturnTime(), returnTime)) {
                return flight;
            }
        }

        return null;
    }

    public Flight findFlightByOneWayCriteria(String departureAirport, String arrivalAirport, String departureTime) {
        List<Flight> allFlights = findAllFlights();

        for (Flight flight : allFlights) {
            if (Objects.equals(flight.getDepartureAirport(), departureAirport) &&
                    Objects.equals(flight.getArrivalAirport(), arrivalAirport) &&
                    Objects.equals(flight.getDepartureTime(), departureTime)) {
                double newCost = flight.getCost() / 2;
                flight.setCost(newCost);
                return flight;
            }
        }

        return null;
    }

    public Flight addFlight(Flight flight) {
        return flightDal.save(flight);
    }

    public List<Flight> findAllFlights() {
        return flightDal.findAll();
    }

    public Flight getFlightById(long flightId) {
        return flightDal.findById(flightId).orElse(null);
    }

    public void deleteFlightById(long flightId) {
        flightDal.deleteById(flightId);
    }
}
