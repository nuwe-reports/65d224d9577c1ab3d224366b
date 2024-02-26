package com.example.demo.controllers;

import com.example.demo.repositories.*;
import com.example.demo.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        appointmentRepository.findAll().forEach(appointments::add);

        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {
            return new ResponseEntity<>(appointment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Validates the appointment received,
     * checks if the appointment overlap with any other appointment
     * and if two conditions are fine, the appointment is created
     *
     * @param appointment Appointment received through the endpoint .
     * @return ResponseEntity with the appointmentList with the new appointment added and an HTTP OK response if successful.
     * ResponseEntity with HTTP NOT_ACCEPTABLE if appointment is overlying with another one.
     * ResponseEntity with HTTP BAD_REQUEST if the appointment doesn't go through the validations.
     */
    @PostMapping("/appointment")
    public ResponseEntity<List<Appointment>> createAppointment(@RequestBody Appointment appointment) {
        if (appointment.getStartsAt() == null || appointment.getFinishesAt() == null || appointment.getStartsAt().isAfter(appointment.getFinishesAt()) || appointment.getStartsAt().isEqual(appointment.getFinishesAt())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Appointment> appointmentsList = appointmentRepository.findAll();

        // NOTE: I do not consider this to be efficient in a database with many appointments. The ideal would be to first extract the logic to a service,
        // query the database and treat this as a batch, fetching the appointments as you iterate through an ordered list and only until an overlap is encountered
        // But this cannot be done due to limitations in editing files other than this one and the test files.
        for (Appointment existingAppointment : appointmentsList) {
            if (appointment.overlaps(existingAppointment)) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);
        appointmentsList.add(savedAppointment);

        return new ResponseEntity<>(appointmentsList, HttpStatus.OK);
    }


    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id) {

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (!appointment.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appointmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments() {
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
