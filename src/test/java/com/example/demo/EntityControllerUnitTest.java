
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests the successful creation of a new doctor via the "POST /api/doctor" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor("Francisco", "Munoz", 30, "f.munoz@email.com");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated());
    }

    /**
     * Tests the successful retrieval of all doctors (the two created in this case) from the  "GET /api/doctors" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldGetTwoDoctors() throws Exception {
        Doctor d1 = new Doctor("Francisco", "Munoz", 30, "f.munoz@email.com");
        Doctor d2 = new Doctor("Lucía", "Munoz", 30, "l.munoz@email.com");

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(d1);
        doctors.add(d2);

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 204 (No Content) response is returned when no doctors exist by getting all by "GET /api/doctors" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotReturnDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests the successful retrieval of a doctor by ID from the "GET /api/doctors/{id}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldGetDoctorById() throws Exception {
        Doctor doctor = new Doctor("Francisco", "Munoz", 30, "f.munoz@email.com");

        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 404 (Not Found) response is returned when attempting to fetch a non-existent doctor by ID from the "GET /api/doctor/{id}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotGetDoctorById() throws Exception {
        long id = 999;

        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the successful deletion of a doctor by ID from the "DELETE /api/doctors/{id}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldDeleteDoctorById() throws Exception {
        Doctor doctor = new Doctor("Francisco", "Munoz", 30, "f.munoz@email.com");

        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 404 (Not Found) response is returned when attempting to delete a
     * non-existent doctor from the "DELETE /api/doctors/{id}"
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotDeleteDoctor() throws Exception {
        long id = 999;

        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the successful deletion of all doctors via the "DELETE /api/doctors/" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldDeleteAllDoctors() throws Exception {
        mockMvc.perform(delete("/api/doctors/"))
                .andExpect(status().isOk());
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Tests the successful creation of a new patient via the "POST /api/patient" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient("Francisco", "Munoz", 30, "f.munoz@email.com");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    /**
     * Tests the successful retrieval of all patients (the two created in this case) from the  "GET /api/patients" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldGetTwoPatients() throws Exception {
        Patient p1 = new Patient("Francisco", "Munoz", 30, "f.munoz@email.com");
        Patient p2 = new Patient("Lucía", "Munoz", 30, "l.munoz@email.com");

        List<Patient> patients = new ArrayList<Patient>();
        patients.add(p1);
        patients.add(p2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 204 (No Content) response is returned when no patients exist by getting all by "GET /api/patients" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotReturnPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests the successful retrieval of a patient by ID from the "GET /api/patients/{id}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldGetPatientById() throws Exception {
        Patient patient = new Patient("Francisco", "Munoz", 30, "f.munoz@email.com");

        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 404 (Not Found) response is returned when attempting to fetch a non-existent patient by ID from the "GET /api/patient/{id}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotGetPatientById() throws Exception {
        long id = 999;

        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the successful deletion of a patient by ID from the "DELETE /api/patients/{id}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldDeletePatientById() throws Exception {
        Patient patient = new Patient("Francisco", "Munoz", 30, "f.munoz@email.com");

        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 404 (Not Found) response is returned when attempting to delete a
     * non-existent patient from the "DELETE /api/patients/{di}"
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotDeletePatient() throws Exception {
        long id = 999;

        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the successful deletion of all patients via the "DELETE /api/patients/" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldDeleteAllPatients() throws Exception {
        mockMvc.perform(delete("/api/patients/"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest {

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests the successful creation of a new room via the "POST /api/room" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room("Room 1");

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    /**
     * Tests the successful retrieval of all rooms (the two created in this case) from the  "GET /api/rooms" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldGetTwoRooms() throws Exception {
        Room r1 = new Room("Room 1");
        Room r2 = new Room("Room 2");

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r1);
        rooms.add(r2);

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 204 (No Content) response is returned when no rooms exist by getting all by "GET /api/rooms" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotReturnRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests the successful retrieval of a room by roomName from the "GET /api/rooms/{roomName}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldGetRoomByRoomName() throws Exception {
        Room room = new Room("Room 1");

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Room 1");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 404 (Not Found) response is returned when attempting to fetch a non-existent room by roomName from the "GET /api/patient/{roomName}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotGetRoomByRoomName() throws Exception {
        String roomName = "Room 999";

        mockMvc.perform(get("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the successful deletion of a room by roomName from the "DELETE /api/rooms/{roomName}" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldDeleteRoomByRoomName() throws Exception {
        Room room = new Room("Room 1");

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Room 1");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a 404 (Not Found) response is returned when attempting to delete a
     * non-existent room from the "DELETE /api/rooms/{roomName}"
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldNotDeleteRoom() throws Exception {
        String roomName = "Room 999";

        mockMvc.perform(delete("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the successful deletion of all patients via the "DELETE /api/rooms/" endpoint.
     *
     * @throws Exception If there are errors during test execution
     */
    @Test
    void shouldDeleteAllRooms() throws Exception {
        mockMvc.perform(delete("/api/rooms/"))
                .andExpect(status().isOk());
    }

}
