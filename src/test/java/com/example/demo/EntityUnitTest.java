package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    @BeforeEach
    void setup() {
        d1 = new Doctor("Fran", "Munoz", 30, "test@doctor.com");
        p1 = new Patient("Lucia", "Munoz", 30, "test@patient.com");
        r1 = new Room("Room 1");
    }

    /**
     * Tests that a Person object created using the empty (no-argument) constructor has default values.
     */
    @Test
    void person_empty_constructor() {
        Person p = new Person();

        assertThat(p.getFirstName()).isNull();
        assertThat(p.getLastName()).isNull();
        assertThat(p.getEmail()).isNull();
        assertThat(p.getAge()).isZero();
    }

    /**
     * Tests that the Person constructor correctly initializes a Person object with the provided values.
     */
    @Test
    void person_constructor_creates_a_person() {
        Person p = new Person("Ramón", "Fernández", 20, "ramonfernandez@gmail.com");

        assertThat(p.getFirstName()).isEqualTo("Ramón");
        assertThat(p.getLastName()).isEqualTo("Fernández");
        assertThat(p.getAge()).isEqualTo(20);
        assertThat(p.getEmail()).isEqualTo("ramonfernandez@gmail.com");
    }

    /**
     * Tests that email setter is working in Person class.
     */
    @Test
    void person_set_email() {
        Person p = new Person();

        p.setEmail("ramonfernandez@gmail.com");
        assertThat(p.getEmail()).isEqualTo("ramonfernandez@gmail.com");
    }

    /**
     * Tests that Doctor constructor creates a Doctor with provided values and that getters for Doctor are working
     */
    @Test
    void doctor_constructor_creates_a_doctor_properly_and_getters_work() {
        d1 = new Doctor("Fran", "Munoz", 30, "test@doctor.com");

        assertThat(d1.getFirstName()).isEqualTo("Fran");
        assertThat(d1.getLastName()).isEqualTo("Munoz");
        assertThat(d1.getAge()).isEqualTo(30);
        assertThat(d1.getId()).isNotNull();
    }

    /**
     * Tests that Doctor is created with its empty constructor.
     */
    @Test
    void doctor_empty_constructor() {
        d1 = new Doctor();

        assertThat(d1.getFirstName()).isNull();
        assertThat(d1.getLastName()).isNull();
        assertThat(d1.getAge()).isEqualTo(0);
        assertThat(d1.getId()).isNotNull();
    }

    /**
     * Tests that doctor setters are working
     */
    @Test
    void doctor_setters_are_working() {
        d1.setId(123);
        assertThat(d1.getId()).isEqualTo(123);

        d1.setFirstName("Paco");
        assertThat(d1.getFirstName()).isEqualTo("Paco");

        d1.setLastName("Beta");
        assertThat(d1.getLastName()).isEqualTo("Beta");

        d1.setAge(20);
        assertThat(d1.getAge()).isEqualTo(20);
    }

    /**
     * Tests that Patient is created using its constructor with the provided values.
     */
    @Test
    void patient_constructor_creates_a_patient_properly_and_getters_work() {
        p1 = new Patient("Lucia", "Munoz", 30, "test@patient.com");

        assertThat(p1.getFirstName()).isEqualTo("Lucia");
        assertThat(p1.getLastName()).isEqualTo("Munoz");
        assertThat(p1.getAge()).isEqualTo(30);
        assertThat(p1.getId()).isNotNull();
    }

    /**
     * Tests Patient is created with empty constructor
     */
    @Test
    void patient_empty_constructor() {
        p1 = new Patient();

        assertThat(p1.getFirstName()).isNull();
        assertThat(p1.getLastName()).isNull();
        assertThat(p1.getAge()).isEqualTo(0);
        assertThat(p1.getId()).isNotNull();
    }

    /**
     * Tests that Patient setters are setting values properly.
     */
    @Test
    void patient_setters_are_working() {
        p1.setId(123);
        assertThat(p1.getId()).isEqualTo(123);

        p1.setFirstName("Lu");
        assertThat(p1.getFirstName()).isEqualTo("Lu");

        p1.setLastName("Beta");
        assertThat(p1.getLastName()).isEqualTo("Beta");

        p1.setAge(20);
        assertThat(p1.getAge()).isEqualTo(20);
    }

    /**
     * Tests that Room constructor is creating a Room with provided values.
     */
    @Test
    void room_constructor_creates_a_room_properly_and_getters_work() {
        r1 = new Room("Room 1");

        assertThat(r1.getRoomName()).isEqualTo("Room 1");
    }

    /**
     * Tests that empty Room constructor is creating a Room with defaults values.
     */
    @Test
    void room_empty_constructor() {
        r1 = new Room();

        assertThat(r1.getRoomName()).isNull();
    }

    /**
     * Tests that Appointment constructor is creating an appointment with provided values and its getters are working.
     */
    @Test
    void appointment_constructor_creates_an_appointment_properly_and_getters_work() {
        LocalDateTime startsAt = LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, startsAt.plusHours(1));

        assertThat(a1.getPatient()).isEqualTo(p1);
        assertThat(a1.getDoctor()).isEqualTo(d1);
        assertThat(a1.getRoom()).isEqualTo(r1);

        assertThat(a1.getStartsAt()).isEqualTo(startsAt);
        assertThat(a1.getFinishesAt()).isEqualTo(finishesAt);
    }


    /**
     * Tests that Appointment empty constructor is creating an Appointment with default values.
     */
    @Test
    void default_appointment_constructor_is_working() {
        Appointment appointment = new Appointment();
        assertThat(appointment.getId()).isNotNull();
        assertThat(appointment.getPatient()).isNull();
        assertThat(appointment.getRoom()).isNull();
        assertThat(appointment.getDoctor()).isNull();
        assertThat(appointment.getStartsAt()).isNull();
        assertThat(appointment.getFinishesAt()).isNull();
    }

    /**
     * Tests that two appointments don't overlap if they are for different rooms.
     */
    @Test
    void appointment_doesnt_overlap_if_different_room() {
        Room r2 = new Room("Another room");

        LocalDateTime startsAt = LocalDateTime.parse("21:30 24/04/2023", formatter);
        a1 = new Appointment(p1, d1, r1, startsAt, startsAt.plusHours(1));
        a2 = new Appointment(p1, d1, r2, startsAt, startsAt.plusHours(1));

        assertThat(a1.overlaps(a2)).isFalse();
    }

    /**
     * Tests that two appointments don't overlap if they are for same room but at different times.
     */
    @Test
    void appointment_doesnt_overlap_same_room_different_times() {
        LocalDateTime aStartsAt = LocalDateTime.parse("21:30 24/04/2023", formatter);
        LocalDateTime aFinishesAt = LocalDateTime.parse("22:30 24/04/2023", formatter);
        LocalDateTime bStartsAt = LocalDateTime.parse("22:30 24/04/2023", formatter);
        LocalDateTime bFinishesAt = LocalDateTime.parse("23:59 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, aStartsAt, aFinishesAt);
        a2 = new Appointment(p1, d1, r1, bStartsAt, bFinishesAt);

        assertThat(a1.overlaps(a2)).isFalse();
    }

    /**
     * Tests that appointment setters are working.
     */
    @Test
    void appointment_setters_are_working() {
        a1.setId(25);
        assertThat(a1.getId()).isEqualTo(25);

        Doctor d2 = new Doctor();

        a1.setDoctor(d2);
        assertThat(a1.getDoctor()).isEqualTo(d2);

        Patient p2 = new Patient();

        a1.setPatient(p2);
        assertThat(a1.getPatient()).isEqualTo(p2);

        Room r2 = new Room();

        a1.setRoom(r2);
        assertThat(a1.getRoom()).isEqualTo(r2);

        LocalDateTime startsAt = LocalDateTime.parse("21:30 24/04/2023", formatter);

        a1.setStartsAt(startsAt);
        assertThat(a1.getStartsAt()).isEqualTo(startsAt);

        LocalDateTime finishesAt = LocalDateTime.parse("22:30 24/04/2023", formatter);
        a1.setFinishesAt(finishesAt);
        assertThat(a1.getFinishesAt()).isEqualTo(finishesAt);
    }

    /**
     * Tests that two appointments overlap when those have the same start date.
     */
    @Test
    void appointment_overlaps_on_equal_starts() {
        LocalDateTime startsAt = LocalDateTime.parse("21:30 24/04/2023", formatter);
        a1 = new Appointment(p1, d1, r1, startsAt, startsAt.plusHours(1));
        a2 = new Appointment(p1, d1, r1, startsAt, startsAt.plusHours(1));

        assertThat(a1.overlaps(a2)).isTrue();
    }

    /**
     * Tests that two appointments overlap when those have the same finish date.
     */
    @Test
    void appointment_overlaps_on_equal_finishes() {
        LocalDateTime finishesAt = LocalDateTime.parse("21:30 24/04/2023", formatter);
        a1 = new Appointment(p1, d1, r1, finishesAt.minusHours(1), finishesAt);
        a2 = new Appointment(p1, d1, r1, finishesAt.minusHours(1), finishesAt);

        assertThat(a1.overlaps(a2)).isTrue();
    }

    /**
     * Tests that two appointments overlap when
     * A start date is before than B finish date
     * and B finish date is before A finish date.
     */
    @Test
    void appointment_overlaps_on_a_starts_before_b_finish_and_b_finishes_before_a_finishes() {
        LocalDateTime aStartsAt = LocalDateTime.parse("21:30 24/04/2023", formatter);
        LocalDateTime aFinishesAt = LocalDateTime.parse("23:30 24/04/2023", formatter);
        LocalDateTime bStartsAt = LocalDateTime.parse("20:30 24/04/2023", formatter);
        LocalDateTime bFinishesAt = LocalDateTime.parse("22:30 24/04/2023", formatter);

        a1.setStartsAt(aStartsAt);
        a1.setFinishesAt(aFinishesAt);

        a2.setStartsAt(bStartsAt);
        a2.setFinishesAt(bFinishesAt);

        assertThat(a1.overlaps(a2)).isTrue();
    }

    /**
     * Tests that two appointments overlap when
     * B start date is after A start date
     * and B start date is before A finish date.
     */
    @Test
    void appointment_overlaps_on_b_starts_after_a_starts_and_b_starts_before_a_finishes() {
        LocalDateTime aStartsAt = LocalDateTime.parse("21:30 24/04/2023", formatter);
        LocalDateTime aFinishesAt = LocalDateTime.parse("23:30 24/04/2023", formatter);
        LocalDateTime bStartsAt = LocalDateTime.parse("22:30 24/04/2023", formatter);
        LocalDateTime bFinishesAt = LocalDateTime.parse("23:30 24/04/2023", formatter);

        a1.setStartsAt(aStartsAt);
        a1.setFinishesAt(aFinishesAt);

        a2.setStartsAt(bStartsAt);
        a2.setFinishesAt(bFinishesAt);

        assertThat(a1.overlaps(a2)).isTrue();
    }

    /**
     * Tests that two appointments don't overlap when they don't have to overlap.
     */
    @Test
    void appointment_does_not_overlap() {
        LocalDateTime aStartsAt = LocalDateTime.parse("21:30 24/04/2023", formatter);
        LocalDateTime aFinishesAt = LocalDateTime.parse("22:30 24/04/2023", formatter);
        LocalDateTime bStartsAt = LocalDateTime.parse("22:30 24/04/2023", formatter); // a finishes when b starts
        LocalDateTime bFinishesAt = LocalDateTime.parse("23:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, aStartsAt, aFinishesAt);
        a2 = new Appointment(p1, d1, r1, bStartsAt, bFinishesAt);

        assertThat(a1.overlaps(a2)).isFalse();
    }

}
