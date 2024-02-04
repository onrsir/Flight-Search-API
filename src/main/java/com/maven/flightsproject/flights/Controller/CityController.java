package com.maven.flightsproject.flights.Controller;

import com.maven.flightsproject.flights.Entities.City;
import com.maven.flightsproject.flights.Services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city/api")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/save")
    public ResponseEntity<City> addCity(@RequestBody City city){
        City addCity = cityService.addCity(city);
        return new ResponseEntity<City>(addCity, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<City>> getAllCities(){
        List<City> allCities = cityService.findAllCities();
        return new ResponseEntity<List<City>>(allCities,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable("id") Long id) {
        City cityById = cityService.getCityById(id);
        if (cityById != null) {
            return new ResponseEntity<City>(cityById, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable("id") Long id){
        cityService.deleteCityById(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
